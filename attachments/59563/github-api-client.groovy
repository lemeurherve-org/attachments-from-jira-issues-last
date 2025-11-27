import com.cloudbees.plugins.credentials.CredentialsMatchers
import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.domains.DomainRequirement
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import hudson.security.ACL
import io.jenkins.plugins.okhttp.api.JenkinsOkHttpClient
import jenkins.model.Jenkins
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.jenkinsci.plugins.github_branch_source.ApiRateLimitChecker
import org.jenkinsci.plugins.github_branch_source.RateLimitExceededException
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.RateLimitHandler
import org.kohsuke.github.authorization.ImmutableAuthorizationProvider
import org.kohsuke.github.extras.okhttp3.OkHttpConnector

def apiUri = "https://api.github.com/"
def host = new URL(apiUri).getHost();
/**
 * Create Username / Password credentials with a GH token
 */
def credentialsId = "testCredentials"

def userPasswordCredentials = CredentialsMatchers.firstOrNull(
  CredentialsProvider.lookupCredentials(UsernamePasswordCredentialsImpl.class, Jenkins.instanceOrNull, ACL.SYSTEM, Collections.<DomainRequirement> emptyList()),
  CredentialsMatchers.allOf(
    CredentialsMatchers.withId(credentialsId),
    CredentialsMatchers.instanceOf(UsernamePasswordCredentialsImpl.class)))
def username = userPasswordCredentials.getUsername()
def password = userPasswordCredentials.getPassword().getPlainText()

def proxyConfig = jenkins.model.Jenkins.get().proxy
def rateLimitHandler = new RateLimitHandler() {

  @Override
  public void onError(IOException e, HttpURLConnection uc) throws IOException {
    try {
      long limit = Long.parseLong(uc.getHeaderField("X-RateLimit-Limit"));
      long remaining = Long.parseLong(uc.getHeaderField("X-RateLimit-Remaining"));
      long reset = Long.parseLong(uc.getHeaderField("X-RateLimit-Reset"));

      throw new RateLimitExceededException(
        "GitHub API rate limit exceeded", limit, remaining, reset);
    } catch (NumberFormatException nfe) {
      // Something wrong happened
      throw new IOException(nfe);
    }
  }
};

def baseClient = JenkinsOkHttpClient.newClientBuilder(new OkHttpClient()).build();
//def baseClient = new OkHttpClient().newBuilder().proxySelector(new JenkinsProxySelector(proxyConfig)).build()
OkHttpClient.Builder clientBuilder = baseClient.newBuilder()
  .proxy(proxyConfig.createProxy(host))
  // Replicate the disk cache
  .cache(new Cache(new File("/tmp/test.cache"), 20 * 1024L * 1024L))

GitHubBuilder gb = new GitHubBuilder();
gb.withEndpoint(apiUri);
gb.withRateLimitChecker(new ApiRateLimitChecker.RateLimitCheckerAdapter());
gb.withRateLimitHandler(rateLimitHandler);
gb.withConnector(new OkHttpConnector(clientBuilder.build()));
gb.withAuthorizationProvider(ImmutableAuthorizationProvider.fromLoginAndPassword(username, password));

def client = gb.build()

client.checkApiUrlValidity()

sleep 30000
/********************************
 * Delete Proxy pod and Service *
 ********************************/

client.getRepository("jenkinsci/github-branch-source-plugin")