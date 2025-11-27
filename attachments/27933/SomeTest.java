/**
 * Copyright Tora Holdings Limited 2014. All rights reserved. 
 * This software is the confidential and proprietary information of Tora Holdings Limited. 
 * Your rights, if any, with respect to the software are governed by your license agreement with Tora.  
 */
package com.mypackage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class description.
 */
public class SomeTest {

	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public SomeTest() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void staticSetUp() throws InterruptedException {
		Thread.sleep(500);
	}

	@AfterClass
	public static void staticTearDown() throws InterruptedException {
		Thread.sleep(500);
	}

	@Before
	public void setUp() throws InterruptedException {
		Thread.sleep(100);
	}

	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(100);
	}

	@Test
	public void testSomething1() throws InterruptedException {
		Thread.sleep(10);
	}

	@Test
	public void testSomething2() throws InterruptedException {
		Thread.sleep(10);
	}

	@Test
	public void testSomething3() throws InterruptedException {
		Thread.sleep(10);
	}
}
