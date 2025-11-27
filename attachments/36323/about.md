Jenkins
=======

Version details
---------------

  * Version: `2.47`
  * Mode:    WAR
  * Url:     https://jenkins-tectonic.prod.coreos.systems/
  * Servlet container
      - Specification: 3.1
      - Name:          `jetty/9.2.z-SNAPSHOT`
  * Java
      - Home:           `/usr/lib/jvm/java-8-openjdk-amd64/jre`
      - Vendor:           Oracle Corporation
      - Version:          1.8.0_121
      - Maximum memory:   1.62 GB (1744830464)
      - Allocated memory: 825.50 MB (865599488)
      - Free memory:      328.32 MB (344273616)
      - In-use memory:    497.18 MB (521325872)
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
      - Version: 25.121-b13
  * Operating system
      - Name:         Linux
      - Architecture: amd64
      - Version:      4.7.3-coreos-r2
  * Process ID: 6 (0x6)
  * Process started: 2017-03-07 20:02:00.848+0000
  * Process uptime: 3 hr 36 min
  * JVM startup parameters:
      - Boot classpath: `/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/sunrsasign.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jfr.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/classes`
      - Classpath: `/usr/share/jenkins/jenkins.war`
      - Library path: `/usr/java/packages/lib/amd64:/usr/lib/x86_64-linux-gnu/jni:/lib/x86_64-linux-gnu:/usr/lib/x86_64-linux-gnu:/usr/lib/jni:/lib:/usr/lib`

Important configuration
---------------

  * Security realm: `org.jenkinsci.plugins.reverse_proxy_auth.ReverseProxySecurityRealm`
  * Authorization strategy: `hudson.security.GlobalMatrixAuthorizationStrategy`
  * CSRF Protection: true
  * Initialization Milestone: Completed initialization

Active Plugins
--------------

  * ace-editor:1.1 'JavaScript GUI Lib: ACE Editor bundle plugin'
  * ansicolor:0.4.3 'AnsiColor'
  * ant:1.4 'Ant Plugin'
  * antisamy-markup-formatter:1.5 'OWASP Markup Formatter Plugin'
  * authentication-tokens:1.3 'Authentication Tokens API Plugin'
  * aws-credentials:1.19 'CloudBees Amazon Web Services Credentials Plugin'
  * aws-java-sdk:1.11.68 'Amazon Web Services SDK'
  * blueocean:1.0.0-b25 'Blue Ocean beta'
  * blueocean-autofavorite:0.6 'blueocean-autofavorite'
  * blueocean-commons:1.0.0-b25 'Common API for Blue Ocean'
  * blueocean-config:1.0.0-b25 'Config API for Blue Ocean'
  * blueocean-dashboard:1.0.0-b25 'Dashboard for Blue Ocean'
  * blueocean-display-url:1.5.1 'BlueOcean Display URL plugin'
  * blueocean-events:1.0.0-b25 'Events API for Blue Ocean'
  * blueocean-git-pipeline:1.0.0-b25 'Git Pipeline for Blue Ocean'
  * blueocean-github-pipeline:1.0.0-b25 'GitHub Pipeline for Blue Ocean'
  * blueocean-i18n:1.0.0-b25 'i18n for Blue Ocean'
  * blueocean-jwt:1.0.0-b25 'JWT for Blue Ocean'
  * blueocean-personalization:1.0.0-b25 'Personalization for Blue Ocean'
  * blueocean-pipeline-api-impl:1.0.0-b25 'Pipeline REST API for Blue Ocean'
  * blueocean-pipeline-editor:0.1-preview-4 'Blue Ocean Pipeline Editor'
  * blueocean-rest:1.0.0-b25 'REST API for Blue Ocean'
  * blueocean-rest-impl:1.0.0-b25 'REST Implementation for Blue Ocean'
  * blueocean-web:1.0.0-b25 'Web for Blue Ocean'
  * bouncycastle-api:2.16.0 'bouncycastle API Plugin'
  * branch-api:2.0.7 'Branch API Plugin'
  * build-metrics:1.3 'build-metrics'
  * build-name-setter:1.6.5 'build-name-setter'
  * build-timeout:1.18 'Jenkins build timeout plugin'
  * buildtriggerbadge:2.8 'Build Trigger Badge Plugin'
  * cloudbees-folder:5.18 'Folders Plugin'
  * config-file-provider:2.15.6 'Config File Provider Plugin'
  * copyartifact:1.38.1 'Copy Artifact Plugin'
  * credentials:2.1.13 'Credentials Plugin'
  * credentials-binding:1.10 'Credentials Binding Plugin'
  * dashboard-view:2.9.10 'Dashboard View'
  * display-url-api:1.1.1 'Display URL API'
  * docker-commons:1.6 'Docker Commons Plugin'
  * docker-workflow:1.10 'Docker Pipeline'
  * durable-task:1.13 'Durable Task Plugin'
  * ec2:1.36 'Amazon EC2 plugin'
  * email-ext:2.57 'Email Extension Plugin'
  * embeddable-build-status:1.9 'embeddable-build-status'
  * external-monitor-job:1.7 'External Monitor Job Type Plugin'
  * external-workspace-manager:1.1.1 'External Workspace Manager Plugin'
  * favorite:2.0.4 'Favorite'
  * ghprb:1.35.0 'GitHub Pull Request Builder'
  * git:3.1.0 'Jenkins Git plugin'
  * git-client:2.3.0 'Jenkins Git client plugin'
  * git-parameter:0.8.0 'Git Parameter Plug-In'
  * git-server:1.7 'Jenkins GIT server Plugin'
  * github:1.26.1 'GitHub plugin'
  * github-api:1.85 'GitHub API Plugin'
  * github-branch-source:2.0.3 'GitHub Branch Source Plugin'
  * github-organization-folder:1.6 'GitHub Organization Folder Plugin'
  * github-pr-comment-build:1.1 'GitHub PR Comment Build Plugin'
  * global-build-stats:1.4 'Hudson global-build-stats plugin'
  * groovy-label-assignment:1.2.0 'Groovy Label Assignment plugin'
  * handlebars:1.1.1 'JavaScript GUI Lib: Handlebars bundle plugin'
  * hubot-steps:1.1.0 'Hubot Pipeline Steps'
  * icon-shim:2.0.3 'Icon Shim Plugin'
  * jackson2-api:2.7.3 'Jackson 2 API Plugin'
  * javadoc:1.4 'Javadoc Plugin'
  * job-restrictions:0.6 'Jenkins Job Restrictions Plugin'
  * jquery:1.11.2-0 'Jenkins jQuery plugin'
  * jquery-detached:1.2.1 'JavaScript GUI Lib: jQuery bundles (jQuery and jQuery UI) plugin'
  * junit:1.20 'JUnit Plugin'
  * kubernetes:0.11 'Kubernetes plugin'
  * ldap:1.14 'LDAP Plugin'
  * lockable-resources:1.11.1 'Lockable Resources plugin'
  * mailer:1.19 'Jenkins Mailer Plugin'
  * mapdb-api:1.0.9.0 'MapDB API Plugin'
  * matrix-auth:1.4 'Matrix Authorization Strategy Plugin'
  * matrix-project:1.8 'Matrix Project Plugin'
  * maven-plugin:2.15.1 'Maven Integration plugin'
  * metrics:3.1.2.9 'Metrics Plugin'
  * momentjs:1.1.1 'JavaScript GUI Lib: Moment.js bundle plugin'
  * node-iterator-api:1.5.0 'Node Iterator API Plugin'
  * pam-auth:1.3 'PAM Authentication plugin'
  * pipeline-build-step:2.4 'Pipeline: Build Step'
  * pipeline-github-lib:1.0 'Pipeline: GitHub Groovy Libraries'
  * pipeline-graph-analysis:1.3 'Pipeline Graph Analysis Plugin'
  * pipeline-input-step:2.5 'Pipeline: Input Step'
  * pipeline-milestone-step:1.3 'Pipeline: Milestone Step'
  * pipeline-model-api:1.0.2 'Pipeline: Model API'
  * pipeline-model-declarative-agent:1.0.2 'Pipeline: Declarative Agent API'
  * pipeline-model-definition:1.0.2 'Pipeline: Model Definition'
  * pipeline-rest-api:2.6 'Pipeline: REST API Plugin'
  * pipeline-stage-step:2.2 'Pipeline: Stage Step'
  * pipeline-stage-tags-metadata:1.0.2 'Pipeline: Stage Tags Metadata'
  * pipeline-stage-view:2.6 'Pipeline: Stage View Plugin'
  * plain-credentials:1.4 'Plain Credentials Plugin'
  * promoted-builds:2.28.1 'Jenkins promoted builds plugin'
  * pubsub-light:1.7 'Jenkins Pub-Sub "light" Bus'
  * rebuild:1.25 'Rebuilder'
  * release:2.6.1 'Jenkins Release Plugin'
  * resource-disposer:0.6 'Resource Disposer Plugin'
  * reverse-proxy-auth-plugin:1.4.0 *(update available)* 'Jenkins Reverse Proxy Auth Plugin'
  * run-selector:1.0.0 'Run Selector Plugin'
  * saferestart:0.3 'Safe Restart Plugin'
  * scm-api:2.0.8 'SCM API Plugin'
  * script-security:1.27 'Script Security Plugin'
  * slack:2.1 'Slack Notification Plugin'
  * sse-gateway:1.15 'Server Sent Events (SSE) Gateway Plugin'
  * ssh-agent:1.14 'SSH Agent Plugin'
  * ssh-credentials:1.13 'SSH Credentials Plugin'
  * ssh-slaves:1.13 'Jenkins SSH Slaves plugin'
  * structs:1.6 'Structs Plugin'
  * subversion:2.7.1 'Jenkins Subversion Plug-in'
  * support-core:2.39 'Support Core Plugin'
  * timestamper:1.8.8 'Timestamper'
  * token-macro:2.0 'Token Macro Plugin'
  * variant:1.1 'Variant Plugin'
  * windows-slaves:1.2 'Windows Slaves Plugin'
  * workflow-aggregator:2.5 'Pipeline'
  * workflow-api:2.12 'Pipeline: API'
  * workflow-basic-steps:2.4 'Pipeline: Basic Steps'
  * workflow-cps:2.29 'Pipeline: Groovy'
  * workflow-cps-global-lib:2.7 'Pipeline: Shared Groovy Libraries'
  * workflow-durable-task-step:2.9 'Pipeline: Nodes and Processes'
  * workflow-job:2.10 'Pipeline: Job'
  * workflow-multibranch:2.13 'Pipeline: Multibranch'
  * workflow-scm-step:2.4 'Pipeline: SCM Step'
  * workflow-step-api:2.9 'Pipeline: Step API'
  * workflow-support:2.13 'Pipeline: Supporting APIs'
  * ws-cleanup:0.32 'Jenkins Workspace Cleanup Plugin'
