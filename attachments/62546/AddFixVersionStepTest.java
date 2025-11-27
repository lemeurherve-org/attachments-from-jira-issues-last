package org.thoughtslive.jenkins.plugins.jira.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import hudson.AbortException;
import java.io.IOException;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.thoughtslive.jenkins.plugins.jira.BaseTest;
import org.thoughtslive.jenkins.plugins.jira.api.ResponseData;
import org.thoughtslive.jenkins.plugins.jira.api.ResponseData.ResponseDataBuilder;

/**
 * Unit test cases for AddFixVersionStep class.
 *
 * @author Stephen Paulin
 */
public class AddFixVersionStepTest extends BaseTest {
  
  final Object version = "component-1.0.0-1";

  AddFixVersionStep.Execution stepExecution;

  @Before
  public void setup() throws IOException, InterruptedException {

    final ResponseDataBuilder<Object> builder = ResponseData.builder();
    when(jiraServiceMock.addFixVersionToIssue(anyString(), any(), any()))
        .thenReturn(builder.successful(true).code(200).message("Success").build());

  }

  @Test
  public void testWithEmptyIdOrKeyThrowsAbortException() throws Exception {
    final AddFixVersionStep step = new AddFixVersionStep("", version);
    stepExecution = new AddFixVersionStep.Execution(step, contextMock);

    // Execute and assert Test.
    assertThatExceptionOfType(AbortException.class).isThrownBy(() -> {
          stepExecution.run();
        }).withMessage("idOrKey is empty or null.").withStackTraceContaining("AbortException")
        .withNoCause();
  }

  @Test
  public void testSuccessfulAddFixVersionToIssue() throws Exception {
    final AddFixVersionStep step = new AddFixVersionStep("TEST-1", version);
    stepExecution = new AddFixVersionStep.Execution(step, contextMock);

    // Execute Test.
    stepExecution.run();

    // Assert Test
    verify(jiraServiceMock, times(1)).addFixVersionToIssue("TEST-1", version, new HashMap<>());
    assertThat(step.isFailOnError()).isEqualTo(true);
  }
}
