/*
 * The MIT License
 *
 * Copyright 2017 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.scm;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.plugins.credentials.domains.Domain;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
import jenkins.scm.impl.subversion.SubversionSampleRepoRule;
import org.apache.commons.io.FileUtils;
import org.junit.ClassRule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.jvnet.hudson.test.BuildWatcher;
import org.jvnet.hudson.test.Issue;
import org.jvnet.hudson.test.JenkinsRule;

public class CredentialsExternalsTest {

    @ClassRule
    public static BuildWatcher buildWatcher = new BuildWatcher();

    @Rule
    public JenkinsRule r = new JenkinsRule();

    /** Main repository. */
    @Rule
    public SubversionSampleRepoRule main = new SubversionSampleRepoRule();

    /** Extension repository. */
    @Rule
    public SubversionSampleRepoRule ext = new SubversionSampleRepoRule();

    @Issue("JENKINS-32167")
    @Ignore
    @Test
    public void smokes() throws Exception {
        main.init();
        main.writeConf("svnserve.conf",
            "[general]\n" +
            "password-db = passwd\n" +
            "authz-db = authz\n" +
            "anon-access = none\n"); // https://bugzilla.redhat.com/show_bug.cgi?id=556712
        main.writeConf("passwd",
            "[users]\n" +
            "alice = alice\n");
        main.writeConf("authz",
            "[/]\n" +
            "alice = rw\n");
        // Adapted from AbstractSubversionTest.runSvnServe:
        int mainPort;
        ServerSocket serverSocket = new ServerSocket(0);
        try {
            mainPort = serverSocket.getLocalPort();
        } finally {
            serverSocket.close();
        }
        Process mainSrv = new ProcessBuilder("svnserve", "-d", "--foreground", "-r", main.root().getAbsolutePath(), "--listen-port", String.valueOf(mainPort)).start();
        try {
            System.err.println("Running svnserve on <svn://localhost:" + mainPort + "> " + main.uuid());
            ext.init();
            ext.writeConf("svnserve.conf",
                "[general]\n" +
                "password-db = passwd\n" +
                "authz-db = authz\n" +
                "anon-access = none\n");
            ext.writeConf("passwd",
                "[users]\n" +
                "bob = bob\n");
            ext.writeConf("authz",
                "[/]\n" +
                "bob = rw\n");
            int extPort;
            serverSocket = new ServerSocket(0);
            try {
                extPort = serverSocket.getLocalPort();
            } finally {
                serverSocket.close();
            }
            Process extSrv = new ProcessBuilder("svnserve", "-d", "--foreground", "-r", ext.root().getAbsolutePath(), "--listen-port", String.valueOf(extPort)).start();
            try {
                System.err.println("Running svnserve on <svn://localhost:" + extPort + "> " + ext.uuid());
                main.svnkit("update", main.wc());
                main.svnkit("propset", "svn:externals", "ext svn://localhost:" + extPort + "/prj/trunk\n", main.wc());
                main.svnkit("commit", "--message=externals", main.wc());
                FreeStyleProject p = r.createFreeStyleProject("p");
                SystemCredentialsProvider.getInstance().setDomainCredentialsMap(Collections.singletonMap(Domain.global(), Arrays.<Credentials>asList(
                    new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, "main-creds", null, "alice", "alice"),
                    new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, "ext-creds", null, "bob", "bob"))));
                p.setScm(new SubversionSCM(
                    Collections.singletonList(new SubversionSCM.ModuleLocation("svn://localhost:" + mainPort + "/prj/trunk", "main-creds", ".", "", false)),
                    null, null, null, null, null, null, null, false, false, // WTF was all that?
                    Collections.singletonList(new SubversionSCM.AdditionalCredentials("<svn://localhost:" + extPort + "> " + ext.uuid(), "ext-creds"))));
                FreeStyleBuild b = r.buildAndAssertSuccess(p);
                assertEquals("", b.getWorkspace().child("file").readToString());
                assertEquals("", b.getWorkspace().child("ext/file").readToString());
                main.write("file", "mainrev");
                main.svnkit("commit", "--message=mainrev", main.wc());
                ext.write("file", "extrev");
                ext.svnkit("commit", "--message=extrev", ext.wc());
                b = r.buildAndAssertSuccess(p);
                assertEquals("mainrev", b.getWorkspace().child("file").readToString());
                assertEquals("extrev", b.getWorkspace().child("ext/file").readToString());
                Set<String> messages = new TreeSet<String>();
                for (ChangeLogSet.Entry entry : b.getChangeSet()) {
                    messages.add(entry.getMsg());
                }
                assertEquals("[extrev, mainrev]", messages.toString());
            } finally {
                extSrv.destroy();
            }
        } finally {
            mainSrv.destroy();
        }
    }

    /**
     * We have two projects in the same repository.  The main project references
     * the second project (prj2) through an external.
     * 
     * This will work correctly, if you set up the external credentials just perfectly.
     * However, you should not need to, as the external credentials and the main
     * project credentials are the same.
     * 
     * @throws Exception 
     */
    @Issue("JENKINS-32167")
    @Test
    public void extFromSameServer() throws Exception {
        // Build the default repository in main
        main.init();
        
        // Build another repository in main (will be our external)
        System.out.println("Building second project - prj2");
        String project2 = "prj2";
        String trunk2 = main.rootUrl()+project2+"/trunk";
        String branches2 = main.rootUrl()+project2+"/branches";
        String tags2 = main.rootUrl()+project2+"/tags";
        main.svnkit("mkdir", "--parents", "--message=structure", trunk2, branches2, tags2);
        // Set up a working directory for project2
        File wc = new File(main.wc());
        File tmp = wc.getParentFile();
        File wc2 = new File(tmp, "prj2wc");
        wc2.delete();
        wc2.mkdirs();
        
        // Check out project2 and stick a file in it
        main.svnkit("co", trunk2, wc2.getAbsolutePath());
        FileUtils.write(new File(wc2.getAbsolutePath(), "file2"), "");
        main.svnkit("add", wc2.getAbsolutePath()+"/file2");
        main.svnkit("commit", "--message=init2", wc2.getAbsolutePath());
        
        // Set up access - only one user is necessary for the repository
        main.writeConf("svnserve.conf",
            "[general]\n" +
            "password-db = passwd\n" +
            "authz-db = authz\n" +
            "anon-access = none\n"); // https://bugzilla.redhat.com/show_bug.cgi?id=556712
        main.writeConf("passwd",
            "[users]\n" +
            "alice = alice\n");
        main.writeConf("authz",
            "[/]\n" +
            "alice = rw\n");
        
        // Start a SVN server - Adapted from AbstractSubversionTest.runSvnServe:
        int mainPort;
        ServerSocket serverSocket = new ServerSocket(0);
        try {
            mainPort = serverSocket.getLocalPort();
        } finally {
            serverSocket.close();
        }
        Process mainSrv = new ProcessBuilder("svnserve", "-d", "--foreground", "-r", main.root().getAbsolutePath(), "--listen-port", String.valueOf(mainPort)).start();
        try {
            System.err.println("Running svnserve on <svn://localhost:" + mainPort + "> " + main.uuid());
            
            // Set up the svn:externals between the main project "prj" and the secondary project "prj2"
            main.svnkit("update", main.wc());
            main.svnkit("propset", "svn:externals", "ext svn://localhost:" + mainPort + "/prj2/trunk\n", main.wc());
            main.svnkit("commit", "--message=externals", main.wc());
            
            // Create a Jenkins Project
            FreeStyleProject p = r.createFreeStyleProject("p");
            // Set up the credentials.  Since the prj and prj2 (the external under prj) are in the same repository
            // we should only need one set of credentials.
            SystemCredentialsProvider.getInstance().setDomainCredentialsMap(Collections.singletonMap(Domain.global(), Arrays.<Credentials>asList(
                new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, "main-creds", null, "alice", "alice"))));
            p.setScm(new SubversionSCM(Collections.singletonList(new SubversionSCM.ModuleLocation("svn://localhost:" + mainPort + "/prj/trunk", "main-creds", ".", "", false)),
                null, null, null, null, null, null, null, false, false, null));
            // Add the AdditionalCredentials below to the SubversionSCM above, in the place of the last null and 
            // everything works correction.  However, the are using the same credentials, so this should be unneccessary
            // and is unnecessary for almost all the SVN calls made by this code.
//                Collections.singletonList(new SubversionSCM.AdditionalCredentials("<svn://localhost:" + mainPort + "> " + main.uuid(), "main-creds"))));
            
            // Start a build of our Jenkins Project
            FreeStyleBuild b = r.buildAndAssertSuccess(p);
            // Make sure the combined workspace can see both files
            assertEquals("", b.getWorkspace().child("file").readToString());
            assertEquals("", b.getWorkspace().child("ext/file2").readToString());
            
            // Write contents to the two files - the write to ext/file2 will trigger our failure
            main.write("file", "mainrev");
            main.svnkit("commit", "--message=mainrev", main.wc());
            FileUtils.write(new File(wc2.getAbsolutePath(), "file2"), "extrev");
            main.svnkit("commit", "--message=extrev", wc2.getAbsolutePath());
            
            // Rerun
            b = r.buildAndAssertSuccess(p);
            // Make sure the combined workspace sees the changes
            assertEquals("mainrev", b.getWorkspace().child("file").readToString());
            assertEquals("extrev", b.getWorkspace().child("ext/file2").readToString());
            Set<String> messages = new TreeSet<String>();
            for (ChangeLogSet.Entry entry : b.getChangeSet()) {
                messages.add(entry.getMsg());
            }
            assertEquals("[extrev, mainrev]", messages.toString());
        } finally {
            mainSrv.destroy();
        }
    }
}
