#!/usr/bin/groovy
/* -*- mode: groovy; -*-
 * Jenkins pipeline testcase greet function
 */

package com.example.jenkins.testcase;

def call(String name)
{
	sh("echo Hello ${name}");
}

def call(String... params) {
        println params
        error("Unknown signature. Abort.");
}
