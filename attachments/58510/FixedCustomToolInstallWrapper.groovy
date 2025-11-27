/**
 Redistributes https://github.com/jenkinsci/custom-tools-plugin
 
 Copyright 2012-2019 CloudBees, Inc. and other contributors
 Copyright 2022 LG Electronics, Inc.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.lge.tpci.utils.wrappers

import com.cloudbees.groovy.cps.NonCPS
import com.cloudbees.jenkins.plugins.customtools.CustomTool
import com.cloudbees.jenkins.plugins.customtools.CustomToolInstallWrapper
import com.synopsys.arc.jenkinsci.plugins.customtools.*
import com.synopsys.arc.jenkinsci.plugins.customtools.multiconfig.MulticonfigWrapperOptions
import hudson.AbortException
import hudson.EnvVars
import hudson.Launcher
import hudson.Proc
import hudson.matrix.MatrixBuild
import hudson.model.AbstractBuild
import hudson.model.BuildListener
import hudson.model.Computer
import hudson.model.Node

class FixedCustomToolInstallWrapper extends CustomToolInstallWrapper
{
    def FixedCustomToolInstallWrapper(CustomToolInstallWrapper.SelectedTool[] selectedTools, MulticonfigWrapperOptions multiconfigOptions, boolean convertHomesToUppercase)
    { super(selectedTools, multiconfigOptions, convertHomesToUppercase) }
    
    @NonCPS
    @Override
    Launcher decorateLauncher(AbstractBuild build, Launcher launcher, BuildListener listener)
    {
        final EnvVars buildEnv = build.getEnvironment(listener)
        final EnvVars homes = new EnvVars()
        final EnvVars versions = new EnvVars()
        
        final PathsList paths = new PathsList()
        final List<EnvVariablesInjector> additionalVarInjectors = [ ]
        
        // Handle multi-configuration build
        if (build instanceof MatrixBuild)
        {
            CustomToolsLogger.logMessage(listener, "Skipping installation of tools at the master job")
            if (getMulticonfigOptions().isSkipMasterInstallation())
                return launcher
        }
        
        // Each tool can export zero or many directories to the PATH
        final Node node = build.builtOn
        if (node == null)
            throw new CustomToolException("Cannot install tools on the deleted node")
        
        selectedTools.each
        {
            final CustomTool tool = it.toCustomToolValidated()
            CustomToolsLogger.logMessage(listener, tool.getName(), "Starting installation")
            
            // Check versioning
            checkVersions(tool, listener, buildEnv, node, versions)
            
            // This installs the tool if necessary
            final CustomTool installed = tool
                .forNode(node, listener)
                .forEnvironment(buildEnv)
                .forBuildProperties(build.getProject().getProperties())
            
            try
            { installed.check() }
            catch (CustomToolException ex)
            { throw new AbortException(ex.getMessage()) }
            
            // Handle global options of the tool
            //TODO: convert to label specifics?
            final PathsList installedPaths = installed.getPaths(node)
            installed.correctHome(installedPaths)
            paths.add(installedPaths)
            final String additionalVars = installed.getAdditionalVariables()
            if (additionalVars != null)
                additionalVarInjectors.add(EnvVariablesInjector.create(additionalVars))
            
            // Handle label-specific options of the tool
            installed.getLabelSpecifics().each
            {
                if (!it.appliesTo(node)) return
                
                CustomToolsLogger.logMessage(listener, installed.getName(), "Label specifics from '" + it.getLabel() + "' will be applied")
                
                final String additionalLabelSpecificVars = it.getAdditionalVars()
                if (additionalLabelSpecificVars != null)
                    additionalVarInjectors.add(EnvVariablesInjector.create(additionalLabelSpecificVars))
                
                CustomToolsLogger.logMessage(listener, installed.getName(), "Tool is installed at " + installed.getHome())
                final String homeDirVarName = (convertHomesToUppercase ? installed.getName().toUpperCase(Locale.ENGLISH) : installed.getName()) + "_HOME"
                CustomToolsLogger.logMessage(listener, installed.getName(), "Setting " + homeDirVarName + "=" + installed.getHome())
                homes.put(homeDirVarName, installed.getHome())
            }
        }
        
        return new Launcher.DecoratedLauncher(launcher) \
        {
            @NonCPS
            @Override
            Proc launch(Launcher.ProcStarter starter) throws IOException
            {
                EnvVars vars
                
                try // Dirty hack, which allows to avoid NPEs in Launcher::envs()
                { vars = toEnvVars(starter.envs()) }
                catch (NullPointerException ignored)
                { vars = new EnvVars() }
                catch (InterruptedException x)
                { throw new IOException(x) }
                
                // Inject paths
                final String injectedPaths = paths.toListString()
                if (injectedPaths != null)
                    vars.override("PATH+", injectedPaths)
                
                // Inject additional variables
                vars.putAll(homes)
                vars.putAll(versions)
                for (EnvVariablesInjector injector : additionalVarInjectors)
                    injector.injectVariables(vars)
                
                // Override paths to prevent JENKINS-20560
                if (vars.containsKey("PATH"))
                {
                    final String overallPaths = vars.get("PATH")
                    vars.remove("PATH")
                    vars.put("PATH+", overallPaths)
                }
                
                return getInner().launch(starter.envs(vars))
            }
            
            @NonCPS
            private EnvVars toEnvVars(String[] envs)
            {
                Computer computer = node.toComputer()
                EnvVars vars = computer != null ? computer.getEnvironment() : new EnvVars()
                for (String line : envs)
                    vars.addLine(line)
                
                return vars
            }
        }
    }
}
