package org.thoughtslive.jenkins.plugins.jira.steps;

import static org.thoughtslive.jenkins.plugins.jira.util.Common.buildErrorResponse;

import hudson.Extension;
import hudson.Util;
import java.io.IOException;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.thoughtslive.jenkins.plugins.jira.api.ResponseData;
import org.thoughtslive.jenkins.plugins.jira.util.JiraStepDescriptorImpl;
import org.thoughtslive.jenkins.plugins.jira.util.JiraStepExecution;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Step to bind a Fix Version to a Jira Issue.
 *
 * @author Stephen Paulin
 */
public class AddFixVersionStep extends BasicJiraStep {

  @Getter
  private final String idOrKey;

  @Getter
  private final Object version;

  @DataBoundConstructor
  public AddFixVersionStep(final String idOrKey, final Object version) {
    this.idOrKey = idOrKey;
    this.version = version;
  }

  @Override
  public StepExecution start(StepContext context) throws Exception {
    return new Execution(this, context);
  }

  @Extension
  public static class DescriptorImpl extends JiraStepDescriptorImpl {

    @Override
    public String getFunctionName() {
      return "jiraAddFixVersion";
    }

    @Override
    public String getDisplayName() {
      return getPrefix() + "Add Fix Version";
    }
  }

  public static class Execution extends JiraStepExecution<ResponseData<Object>> {


    private final AddFixVersionStep step;

    protected Execution(final AddFixVersionStep step, final StepContext context)
        throws IOException, InterruptedException {
      super(context);
      this.step = step;
    }

    @Override
    protected ResponseData<Object> run() throws Exception {

      ResponseData<Object> response = verifyInput();

      if (response == null) {
        logger.println("JIRA: Site - " + siteName + " - Add Fix Version: " + step.getVersion() +" to Issue: " + step.getIdOrKey()) ;
        response = jiraService
            .addFixVersionToIssue(step.getIdOrKey(), step.getVersion(), step.getQueryParams());
      }

      return logResponse(response);
    }

    @Override
    protected <T> ResponseData<T> verifyInput() throws Exception {
      String errorMessage = null;
      ResponseData<T> response = verifyCommon(step);

      if (response == null) {
        final String idOrKey = Util.fixEmpty(step.getIdOrKey());
        final Object version = step.getVersion();

        if (idOrKey == null) {
          errorMessage = "idOrKey is empty or null.";
        }

        if (version == null) {
          errorMessage = "fix version is null.";
        }

        if (errorMessage != null) {
          response = buildErrorResponse(new RuntimeException(errorMessage));
        }
      }
      return response;
    }
  }
}
