Jenkins
=======

Version details
---------------

  * Version: `2.32.2.1-rolling`
  * Mode:    WAR
  * Url:     https://master.alliances.beescloud.com/
  * Servlet container
      - Specification: 3.1
      - Name:          `jetty/9.2.z-SNAPSHOT`
  * Java
      - Home:           `/usr/lib/jvm/java-8-openjdk-amd64/jre`
      - Vendor:           Oracle Corporation
      - Version:          1.8.0_111
      - Maximum memory:   878.50 MB (921174016)
      - Allocated memory: 541.50 MB (567803904)
      - Free memory:      103.58 MB (108608640)
      - In-use memory:    437.92 MB (459195264)
      - GC strategy:      ParallelGC
  * Java Runtime Specification
      - Name:    Java Platform API Specification
      - Vendor:  Oracle Corporation
      - Version: 1.8
  * JVM Specification
      - Name:    Java Virtual Machine Specification
      - Vendor:  Oracle Corporation
      - Version: 1.8
  * JVM Implementation
      - Name:    OpenJDK 64-Bit Server VM
      - Vendor:  Oracle Corporation
      - Version: 25.111-b14
  * Operating system
      - Name:         Linux
      - Architecture: amd64
      - Version:      3.13.0-108-generic
      - Distribution: Ubuntu 14.04.5 LTS
  * Process ID: 1128 (0x468)
  * Process started: 2017-02-06 10:33:50.466+0000
  * Process uptime: 18 min
  * JVM startup parameters:
      - Boot classpath: `/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/sunrsasign.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jfr.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/classes`
      - Classpath: `/usr/share/jenkins/jenkins.war`
      - Library path: `/usr/java/packages/lib/amd64:/usr/lib/x86_64-linux-gnu/jni:/lib/x86_64-linux-gnu:/usr/lib/x86_64-linux-gnu:/usr/lib/jni:/lib:/usr/lib`
      - arg[0]: `-Djava.awt.headless=true`

Important configuration
---------------

  * Security realm: `hudson.security.HudsonPrivateSecurityRealm`
  * Authorization strategy: `nectar.plugins.rbac.strategy.RoleMatrixAuthorizationStrategyImpl`
  * CSRF Protection: true
  * Initialization Milestone: Completed initialization

Active Plugins
--------------

  * ace-editor:1.1 'JavaScript GUI Lib: ACE Editor bundle plugin'
  * active-directory:1.48 'Jenkins Active Directory plugin'
  * amazon-ecr:1.4 'Amazon ECR plugin'
  * amazon-ecs:1.9 'Amazon EC2 Container Service plugin'
  * analysis-collector:1.50 'Static Analysis Collector Plug-in'
  * analysis-core:1.82 'Static Analysis Utilities'
  * ansicolor:0.4.3 'AnsiColor'
  * ant:1.4 'Ant Plugin'
  * antisamy-markup-formatter:1.5 'OWASP Markup Formatter Plugin'
  * async-http-client:1.7.24.1 'Async Http Client'
  * authentication-tokens:1.3 'Authentication Tokens API Plugin'
  * aws-credentials:1.16 'CloudBees Amazon Web Services Credentials Plugin'
  * aws-java-sdk:1.11.37 'Amazon Web Services SDK'
  * azure-cli:1.1 'CloudBees Azure CLI Plugin'
  * azure-publishersettings-credentials:1.2 'Azure PublisherSettings Credentials Plugin'
  * blueocean:1.0.0-b11 *(update available)* 'BlueOcean beta'
  * blueocean-commons:1.0.0-b22 'Common API for Blue Ocean'
  * blueocean-config:1.0.0-b21 *(update available)* 'Config API for Blue Ocean'
  * blueocean-dashboard:1.0.0-b14 *(update available)* 'Dashboard for Blue Ocean'
  * blueocean-display-url:1.3 'BlueOcean Display URL plugin'
  * blueocean-events:1.0.0-b14 *(update available)* 'Events API for Blue Ocean'
  * blueocean-jwt:1.0.0-b21 'JWT for Blue Ocean'
  * blueocean-personalization:1.0.0-b14 *(update available)* 'Personalization for Blue Ocean'
  * blueocean-pipeline-api-impl:1.0.0-b21 'Pipeline REST API for Blue Ocean'
  * blueocean-rest:1.0.0-b22 'REST API for Blue Ocean'
  * blueocean-rest-impl:1.0.0-b21 'REST Implementation for Blue Ocean'
  * blueocean-web:1.0.0-b22 'Web for Blue Ocean'
  * bouncycastle-api:2.16.0 'bouncycastle API Plugin'
  * branch-api:1.11.1 'Branch API Plugin'
  * build-timeout:1.18 'Jenkins build timeout plugin'
  * build-view-column:0.3 'Build View Column Plugin'
  * checkstyle:3.47 'Checkstyle Plug-in'
  * cloudbees-aborted-builds:1.9 'CloudBees Restart Aborted Builds Plugin'
  * cloudbees-assurance:2.32.0.1 'Beekeeper Upgrade Assistant Plugin'
  * cloudbees-aws-cli:1.5.6 'CloudBees Amazon AWS CLI Plugin'
  * cloudbees-aws-credentials:1.8.4 'Former CloudBees Amazon Web Services Credentials Plugin (no longer in use)'
  * cloudbees-aws-deployer:1.17 'CloudBees Amazon Web Services Deploy Engine Plugin'
  * cloudbees-bitbucket-branch-source:1.8 'Bitbucket Branch Source Plugin'
  * cloudbees-cloudfoundry-cli:2.1.3 'Cloud Foundry CLI Plugin'
  * cloudbees-consolidated-build-view:1.5 'CloudBees Consolidated Build View Plugin'
  * cloudbees-credentials:3.3 'CloudBees Credentials Plugin'
  * cloudbees-even-scheduler:3.7 'CloudBees Even Scheduler Plugin'
  * cloudbees-folder:5.13 'Folders Plugin'
  * cloudbees-folders-plus:3.0 'CloudBees Folders Plus Plugin'
  * cloudbees-github-pull-requests:1.1 'CloudBees GitHub Pull Requests Plugin'
  * cloudbees-groovy-view:1.5 'CloudBees Groovy View Plugin'
  * cloudbees-ha:4.7 'CloudBees High Availability Management plugin'
  * cloudbees-jsync-archiver:5.5 'CloudBees Fast Archiving Plugin'
  * cloudbees-label-throttling-plugin:3.4 'CloudBees Label Throttling Plugin'
  * cloudbees-license:9.3 'CloudBees License Manager'
  * cloudbees-long-running-build:1.9 'CloudBees Long-Running Build Plugin'
  * cloudbees-monitoring:2.5 'CloudBees Monitoring Plugin'
  * cloudbees-nodes-plus:1.14 'CloudBees Nodes Plus Plugin'
  * cloudbees-plugin-usage:1.6 'CloudBees Plugin Usage Plugin'
  * cloudbees-quiet-start:1.2 'CloudBees Quiet Start Plugin'
  * cloudbees-request-filter:1.2 'CloudBees Request Filter Plugin'
  * cloudbees-secure-copy:3.9 'CloudBees Secure Copy Plugin'
  * cloudbees-ssh-slaves:1.7 'CloudBees SSH Build Agents Plugin'
  * cloudbees-support:3.7 'CloudBees Support Plugin'
  * cloudbees-template:4.26 'CloudBees Template Plugin'
  * cloudbees-view-creation-filter:1.3 'CloudBees View Creation Filter Plugin'
  * cloudbees-wasted-minutes-tracker:3.8 'CloudBees Wasted Minutes Tracker Plugin'
  * cloudbees-workflow-aggregator:1.9.1 'CloudBees Pipeline (Deprecated)'
  * cloudbees-workflow-rest-api:1.9.1 'CloudBees Pipeline: REST API (Deprecated)'
  * cloudbees-workflow-template:2.5 'CloudBees Pipeline: Templates Plugin'
  * cloudbees-workflow-ui:2.1 'CloudBees Pipeline Stage View Extensions'
  * conditional-buildstep:1.3.5 'Conditional BuildStep'
  * config-file-provider:2.15.5 'Config File Provider Plugin'
  * copyartifact:1.38.1 'Copy Artifact Plugin'
  * credentials:2.1.10 'Credentials Plugin'
  * credentials-binding:1.10 'Credentials Binding Plugin'
  * dashboard-view:2.9.10 'Dashboard View'
  * deployed-on-column:1.7 'Deployed On Column Plugin'
  * deployer-framework:1.1 'Deployer Framework Plugin'
  * display-url-api:0.5 'Display URL API'
  * docker-build-publish:1.3.2 'CloudBees Docker Build and Publish plugin'
  * docker-commons:1.5 'Docker Commons Plugin'
  * docker-custom-build-environment:1.6.5 'CloudBees Docker Custom Build Environment Plugin'
  * docker-traceability:1.2 'CloudBees Docker Traceability'
  * docker-workflow:1.9.1 'Docker Pipeline'
  * dockerhub-notification:2.2.0 'CloudBees Docker Hub/Registry Notification'
  * durable-task:1.12 'Durable Task Plugin'
  * ec2:1.36 'Amazon EC2 plugin'
  * external-monitor-job:1.6 'External Monitor Job Type Plugin'
  * favorite:2.0.4 'Favorite'
  * findbugs:4.69 'FindBugs Plug-in'
  * git:3.0.1 'Jenkins Git plugin'
  * git-client:2.2.0 'Jenkins Git client plugin'
  * git-server:1.7 'Jenkins GIT server Plugin'
  * git-validated-merge:3.20 'CloudBees Git Validated Merge Plugin'
  * github:1.25.0 'GitHub plugin'
  * github-api:1.77 'GitHub API Plugin'
  * github-branch-source:1.10 'GitHub Branch Source Plugin'
  * github-oauth:0.25 'GitHub Authentication plugin'
  * github-organization-folder:1.5 'GitHub Organization Folder Plugin'
  * github-pull-request-build:1.10 'CloudBees Pull Request Builder for GitHub'
  * handlebars:1.1.1 'JavaScript GUI Lib: Handlebars bundle plugin'
  * http_request:1.8.13 'HTTP Request Plugin'
  * icon-shim:2.0.3 'Icon Shim Plugin'
  * infradna-backup:3.33-SNAPSHOT (private-fe6b5dbf-cleclerc) 'CloudBees Back-up Plugin'
  * jackson2-api:2.7.3 'Jackson 2 API Plugin'
  * javadoc:1.4 'Javadoc Plugin'
  * job-dsl:1.57 'Job DSL'
  * jquery:1.11.2-0 'Jenkins jQuery plugin'
  * jquery-detached:1.2.1 'JavaScript GUI Lib: jQuery bundles (jQuery and jQuery UI) plugin'
  * junit:1.19 'JUnit Plugin'
  * kubernetes:0.10 'Kubernetes plugin'
  * ldap:1.13 'LDAP Plugin'
  * m2release:0.14.0 'Jenkins Maven Release Plug-in Plug-in'
  * mailer:1.18 'Jenkins Mailer Plugin'
  * mapdb-api:1.0.9.0 'MapDB API Plugin'
  * matrix-auth:1.4 'Matrix Authorization Strategy Plugin'
  * matrix-project:1.7.1 'Matrix Project Plugin'
  * maven-plugin:2.14 'Maven Integration plugin'
  * mercurial:1.57 'Jenkins Mercurial plugin'
  * mesos:0.14.0 'mesos'
  * metrics:3.1.2.9 'Metrics Plugin'
  * momentjs:1.1.1 'JavaScript GUI Lib: Moment.js bundle plugin'
  * monitoring:1.62.0 'Monitoring'
  * nectar-license:8.3 'CloudBees Jenkins Enterprise License Entitlement Check'
  * nectar-rbac:5.12 'CloudBees Role-Based Access Control Plugin'
  * nectar-vmware:4.3.5 'CloudBees VMWare Autoscaling Plugin'
  * node-iterator-api:1.5 'Node Iterator API Plugin'
  * nodejs:0.2.1 'NodeJS Plugin'
  * openid:2.1.1 'openid'
  * openid4java:0.9.8.0 'OpenID4Java API'
  * openshift-cli:1.3 'CloudBees OpenShift CLI Plugin'
  * operations-center-agent:2.32.0.1 'Operations Center Agent'
  * operations-center-analytics-config:2.32.0.1 'Operations Center Analytics Configuration'
  * operations-center-analytics-reporter:2.32.0.1 'Operations Center Analytics Reporter'
  * operations-center-client:2.32.0.1 'Operations Center Client Plugin'
  * operations-center-cloud:2.32.0.1 'Operations Center Cloud'
  * operations-center-context:2.32.0.7 'Operations Center Context'
  * operations-center-openid-cse:1.8.110 'Operations Center OpenID Cluster Session Extension'
  * pam-auth:1.3 'PAM Authentication plugin'
  * parameterized-trigger:2.32 'Jenkins Parameterized Trigger plugin'
  * performance:2.0 'Performance plugin'
  * pipeline-build-step:2.4 'Pipeline: Build Step'
  * pipeline-graph-analysis:1.3 'Pipeline Graph Analysis Plugin'
  * pipeline-input-step:2.5 'Pipeline: Input Step'
  * pipeline-maven:2.0-SNAPSHOT (private-8bae988f-cleclerc) 'Pipeline Maven Integration Plugin'
  * pipeline-milestone-step:1.3 'Pipeline: Milestone Step'
  * pipeline-model-api:1.0 'Pipeline: Model API'
  * pipeline-model-declarative-agent:1.0 'Pipeline: Declarative Agent API'
  * pipeline-model-definition:1.0 'Pipeline: Model Definition'
  * pipeline-rest-api:2.4 'Pipeline: REST API Plugin'
  * pipeline-stage-step:2.2 'Pipeline: Stage Step'
  * pipeline-stage-tags-metadata:1.0 'Pipeline: Stage Tags Metadata'
  * pipeline-stage-view:2.4 'Pipeline: Stage View Plugin'
  * pipeline-utility-steps:1.2.2 'Pipeline Utility Steps'
  * plain-credentials:1.3 'Plain Credentials Plugin'
  * pmd:3.46 'PMD Plug-in'
  * promoted-builds:2.28 'Jenkins promoted builds plugin'
  * release:2.6.1 'Jenkins Release Plugin'
  * run-condition:1.0 'Run Condition Plugin'
  * scm-api:1.3 'SCM API Plugin'
  * script-security:1.25 'Script Security Plugin'
  * skip-plugin:4.0 'CloudBees Skip Next Build Plugin'
  * sse-gateway:1.10 *(update available)* 'Server Sent Events (SSE) Gateway Plugin'
  * ssh-agent:1.13 'SSH Agent Plugin'
  * ssh-credentials:1.12 'SSH Credentials Plugin'
  * ssh-slaves:1.11 'Jenkins SSH Slaves plugin'
  * structs:1.5 'Structs Plugin'
  * support-core:2.38 'Support Core Plugin'
  * suppress-stack-trace:1.5 'Stack Trace Suppression Plugin'
  * token-macro:2.0 'Token Macro Plugin'
  * translation:1.15 'Jenkins Translation Assistance plugin'
  * unique-id:2.1.3 'Unique ID Library Plugin'
  * variant:1.1 'Variant Plugin'
  * warnings:4.59 'Warnings Plug-in'
  * wikitext:3.7 'CloudBees WikiText Security Plugin'
  * windows-azure-storage:0.3.2 'Windows Azure Storage plugin'
  * windows-slaves:1.2 'Windows Slaves Plugin'
  * workflow-aggregator:2.4 'Pipeline'
  * workflow-api:2.8 'Pipeline: API'
  * workflow-basic-steps:2.3 'Pipeline: Basic Steps'
  * workflow-cps:2.24 'Pipeline: Groovy'
  * workflow-cps-checkpoint:2.4 'CloudBees Pipeline: Groovy Checkpoint Plugin'
  * workflow-cps-global-lib:2.5 'Pipeline: Shared Groovy Libraries'
  * workflow-durable-task-step:2.8 'Pipeline: Nodes and Processes'
  * workflow-job:2.9 'Pipeline: Job'
  * workflow-multibranch:2.9.2 'Pipeline: Multibranch'
  * workflow-scm-step:2.3 'Pipeline: SCM Step'
  * workflow-step-api:2.7 'Pipeline: Step API'
  * workflow-support:2.12 'Pipeline: Supporting APIs'

Packaging details
-----------------

AWS


License details
---------------

 * Jenkins Instance ID:  `f03317cdb2f11feee1736d5900cde87c`
 * Expires:              Aug 15, 2017
 * Issued to:            Cyrille Le Clerc
 * Organization:         CloudBees
 * Edition:              Enterprise Edition
     - Starter Pack
     - Enterprise Analytics
     - Enterprise Security
     - VMware vSphere Builds
     - Amazon Web Services Solution Pack
     - Pivotal Solution Pack
     - Microsoft Azure Solution Pack
     - Security
     - OpenShift Solution Pack
     - Analytics
     - Developer Productivity
     - Core
     - Build & Master Resilience
     - Optimized Utilization
     - Enterprise Management
     - Enterprise Continuous Delivery
     - Jenkins Enterprise license with 50 executors
