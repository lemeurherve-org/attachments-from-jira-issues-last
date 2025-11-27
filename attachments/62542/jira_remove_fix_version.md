+++
title = "Issue - Remove Fix Version"
description = "More about jiraRemoveFixVersion step."
tags = ["steps", "issue", "version"]
weight = 11
date = "2024-05-13"
lastmodifierdisplayname = "Stephen Paulin"
+++

### jiraRemoveFixVersion

Supports removing a Fix Version from a Jira issue.

#### Input

* **idOrKey** - issue id or key.
* **version** - The existing version name to apply.
* **queryParams** - Optional. Map of query parameters. 
* **site** - Optional, default: `JIRA_SITE` environment variable.
* **failOnError** - Optional. default: `true`.

#### Output

* Each step generates generic output, please refer to this [link]({{%relref "getting-started/config/common.md"%}}) for more information.
* You can see some example scenarios [here]({{%relref "getting-started/examples"%}})

#### Examples

* With default [site]({{%relref "getting-started/config/common.md#global-environment-variables"%}}) from global variables.

    ```groovy
    node {
      stage('JIRA') {
        def response = jiraRemoveFixVersion idOrKey: "TEST-01", version: "MyVersion-1.0.0"
      
        echo response.successful.toString()
      }
    }
    ```
