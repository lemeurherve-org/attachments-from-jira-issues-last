package com.mycompany.myproject.testscripts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mycompany.myproject.common.BaseTest;

public class ConceptTest extends BaseTest  {
	@Test
	public void JenkinsJob2Test11() {
		WebDriver driver = getDriver();
		driver.get("http://localhost:8080/jenkins/job/jbehave-automation-2/");
		Assert.assertEquals("jbehave-automation-2 [Jenkins]", driver.getTitle());

	}

	@Test(groups={"SmokeTest"})
	public void JenkinsJob1Test11() {
		WebDriver driver = getDriver();
		driver.get("http://localhost:8080/jenkins/job/jbehave-automation-1/");
		Assert.assertEquals("jbehave-automation-1 [Jenkins]", driver.getTitle());
	}

	@Test
	public void TestLinkTest11() {
		WebDriver driver = getDriver();
		driver.get("http://localhost/testlink-1.9.9/login.php");
		Assert.assertEquals("TestLink", driver.getTitle());
	}

	@Test
	public void JenkinsHomeInest11() {
		WebDriver driver = getDriver();
		driver.get("http://localhost:8080/jenkins/");
		Assert.assertEquals("Dashboard [Jenkins]", driver.getTitle());
	}	


}