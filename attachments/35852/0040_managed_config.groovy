import org.jenkinsci.lib.configprovider.ConfigProvider;
import org.jenkinsci.lib.configprovider.model.Config;
import org.jenkinsci.lib.configprovider.model.ContentType;
import org.jenkinsci.plugins.configfiles.custom.CustomConfig;
import java.util.UUID;
import jenkins.model.*
import hudson.security.*
import groovy.json.JsonSlurper
import net.sf.json.JSONObject

//https://github.com/jenkinsci/config-file-provider-plugin/blob/master/src/main/java/org/jenkinsci/plugins/configfiles/ConfigFilesManagement.java

class CustomConfigConfigurer {
  final String configurationFile = "/usr/share/jenkins/ref/config/managed-config.conf.json";

  final Map providerTypeToId = [
     custom: "org.jenkinsci.plugins.configfiles.custom.CustomConfig" ,
     json: "org.jenkinsci.plugins.configfiles.json.JsonConfig" ,
     xml: "org.jenkinsci.plugins.configfiles.xml.XmlConfig",
     groovy: "org.jenkinsci.plugins.configfiles.groovy.GroovyScript",
     extmail_groovy: "hudson.plugins.emailext.GroovyTemplateConfig",
     extmail_jelly: "hudson.plugins.emailext.JellyTemplateConfig"
  ]

  JSONObject customConfig;
  def env;
  def instance;

  def CustomConfigConfigurer() {
    env = System.getenv();
    instance = Jenkins.getInstance();
  }

  def run() {
    // if (!isCredentialConfigEnabled()) return
    def inputFile = new File(configurationFile);
    customConfig = new JsonSlurper().parseText(inputFile.text);
    createCustomConfigs(customConfig);
  }

  def createCustomConfigs(customConfig) {
    customConfig.each { providerType ->
		    def provider = getProviderByType(providerType.getKey());
        new CustomConfigBuilder().loadConfigForProvider(provider, providerType.getValue());
    }
  }

  def getProviderByType(providerType) {
    String providerId = providerTypeToId.get(providerType)
    ConfigProvider provider = ConfigProvider.getByIdOrNull(providerId);
  }

  // def isConfigEnabled() {
  //   if (!env['ENOVA_CREDENTIALS_CONFIG'].toBoolean()) {
  //     println "--> Credentials config disabled"
  //     return false
  //   }
  //   true
  // }
}


class CustomConfigBuilder {

  final String fileLocation = "/usr/share/jenkins/ref/resources/managedConfigFiles/";

  def loadConfigForProvider(provider, configs) {
    configs.each { spec ->
      def config = new Config(spec.id, spec.name, spec.comment, getContentForSpec(spec))
      config.setProviderId(provider.getProviderId());
      provider.save(config);
    }
  }

  String getContentForSpec(spec) {
    String result = null
    if (spec.fileContent) {
      File source = new File( fileLocation + "/" + spec.fileContent)
      result = source.text
    } else {
      result = spec.content
    }
  }

}

new CustomConfigConfigurer().run();
true
