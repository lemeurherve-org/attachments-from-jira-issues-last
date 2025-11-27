import jenkins.model.*
import hudson.model.*
import hudson.tools.*
import hudson.security.*
import hudson.*
import jenkins.*
import java.util.*
import java.lang.reflect.*
import java.util.logging.*
import groovy.json.*
import com.michelin.cio.hudson.plugins.rolestrategy.*
import hudson.security.csrf.DefaultCrumbIssuer
import java.util.logging.Logger
import hudson.plugins.active_directory.*


LOGGER = Logger.getLogger("")
JENKINS_URL = System.getenv()['JENKINS_URL']
JENKINS_INSTANCE = Jenkins.getInstance()

GLOBAL_ADMIN_GROUP_MAP = [
    "https://jenkins.esteeonline.com/":       [ "JNK-Admin"],
    'https://jenkins.esteeonline.cn/':        [ "JNK-Admin"],
    'https://jenkins-staging.elco-cloud.cn/': [ "JNK-Admin"],
    'https://jenkins-migration.elco.cloud/':  [ "JNK-Sandbox-Admin", "JNK-AWS-DevOps", "admin" ]
]

GLOBAL_POWERUSER_GROUP_MAP = [
    "https://jenkins.esteeonline.com/":       [ "aws-jenkinsbind", "JNK-PowerUser"],
    'https://jenkins.esteeonline.cn/':        [ "aws-jenkinsbind" ],
    'https://jenkins-staging.elco-cloud.cn/': [ "aws-jenkinsbind" ],
    'https://jenkins-migration.elco.cloud/':  [ "aws-jenkinsbind", "JNK-Sandbox-User" ]
]


GLOBAL_ADMIN_ROLE = [
  "admin":[
      "permissions": [
          "hudson.model.Hudson.Administer",
          "hudson.model.Hudson.Read",
          "hudson.model.Hudson.SystemRead",
          "hudson.model.Hudson.RunScripts",
          "hudson.model.Hudson.ConfigureUpdateCenter",
          "hudson.model.Hudson.UploadPlugins",
          "com.cloudbees.plugins.credentials.CredentialsProvider.Create",
          "com.cloudbees.plugins.credentials.CredentialsProvider.Delete",
          "com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains",
          "com.cloudbees.plugins.credentials.CredentialsProvider.Update",
          "com.cloudbees.plugins.credentials.CredentialsProvider.View",
          "hudson.model.Computer.Build",
          "hudson.model.Computer.Configure",
          "hudson.model.Computer.Connect",
          "hudson.model.Computer.Create",
          "hudson.model.Computer.Delete",
          "hudson.model.Computer.Disconnect",
          "hudson.model.Computer.ExtendedRead",
          "hudson.model.Computer.Provision",
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Configure",
          "hudson.model.Item.Create",
          "hudson.model.Item.Delete",
          "hudson.model.Item.Discover",
          "hudson.model.Item.ExtendedRead",
          "hudson.model.Item.Move",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Delete",
          "hudson.model.Run.Replay",
          "hudson.model.Run.Update",
          "hudson.model.View.Configure",
          "hudson.model.View.Create",
          "hudson.model.View.Delete",
          "hudson.model.View.Read",
          "hudson.scm.SCM.Tag"
    ],
    "group": GLOBAL_ADMIN_GROUP_MAP.get(JENKINS_URL, [])
  ]
]
GLOBAL_POWERUSER_ROLES = [
  "poweruser": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Configure",
          "hudson.model.Item.Create",
          "hudson.model.Item.Delete",
          "hudson.model.Item.Discover",
          "hudson.model.Item.ExtendedRead",
          "hudson.model.Item.Move",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Delete",
          "hudson.model.Run.Replay",
          "hudson.model.Run.Update",
          "hudson.model.View.Configure",
          "hudson.model.View.Create",
          "hudson.model.View.Delete",
          "hudson.model.View.Read",
          "hudson.scm.SCM.Tag"
      ],
      "group": GLOBAL_POWERUSER_GROUP_MAP.get(JENKINS_URL, [])
  ]
]
GLOBAL_READONLY_ROLES = [
    "ovearall_system_read":[
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Computer.ExtendedRead",
          "com.cloudbees.plugins.credentials.CredentialsProvider.View",
      ],
      "group": ["JNK-AWS-DevOps"]
  ],
  "ovearall_read":[
      "permissions": [
          "hudson.model.Hudson.Read"
      ],
      "group": ["JNK-AWS-Global-User", "JNK-UsersAll", "JNK-AWS-Sd-Be-Prod-Deploy", "JNK-AWS-Sd-Fe-Prod-Deploy", "JNK-AWS-Sd-Ma-Prod"]
  ]
]


PROJECT_NCSA_DEFAULT_ROLES = [
    "JNK-UsersAll": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Personal Server Content Sync",
          "^NCSA/Drupal_pr-build"
      ],
      "group": ["JNK-UsersAll"]
  ]
]
PROJECT_NCSA_ROLES = [
  "JNK-Deploy-Drupal-Feature": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/AWS Feature Test.*",
          "^NCSA/Deploy to Ziggy",
          "^NSCA/AWS Feature Test Multi-Brand - Apache"
        ],
      "group": ["JNK-Deploy-Drupal-Feature"]
  ],
  "JNK-Deploy-Drupal-Feature-Extra":[
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Feature Test.*"
      ],
      "group": ["JNK-Deploy-Drupal-Feature-Extra"]
  ],
  "JNK-AWS-Unlimited-Feature":[
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/AWS LongTerm Feature Test"
      ],
      "group": ["JNK-AWS-Unlimited-Feature"]
  ],
  "JNK-Deploy-React-Feature": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Cancel",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/AWS React -.*"
      ],
      "group": ["JNK-Deploy-React-Feature"]
  ],
  "JNK-AWS-Varnish": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Cancel",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Varnish - varnishgather",
          "^NCSA/AWS Feature Test - Varnish",
          "^NCSA/Varnish - Update default.vcl on non-prod",
          "^NCSA/Varnish Cluster Cron Clear"
      ],
      "group": ["JNK-AWS-Varnish"]
  ],
  "JNK-Alerting": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Configure",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/AWS Linting.*"
      ],
      "group": ["JNK-Alerting"]
  ],
  "JNK-CMS-Read-Only": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/cms_release_test",
          "^NCSA/QA_Sanity_Check"
      ],
      "group": ["JNK-CMS-Read-Only"]
  ],
  "JNK-Cache-Clearer": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/.*products.*",
          "^NCSA/Clear Drupal Caches",
          "^NCSA/Drupal Fun Clear",
          "^NCSA/Clear Drupal Akamai",
          "^NCSA/Clear Drupal Caches Dev or Stage",
          "^NCSA/Clear Drupal Caches Simplified",
          "^NCSA/Clear js-repo by brand",
          "^NCSA/Delete Drupal Urls Akamai",
          "^NCSA/Tools - Cache Warmer",
          "^NCSA/QA_Sanity_Check",
          "^NCSA/Drupal SSR.*"
      ],
      "group": ["JNK-Cache-Clearer"]
  ],
  "JNK-Commit-Log-Generator": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Tools - Generate Commit Log"
      ],
      "group": ["JNK-Commit-Log-Generator"]
  ],
  "JNK-Genie-gradle-QA": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Genie gradle.*"
      ],
      "group": ["JNK-Genie-gradle-QA"]
  ],
  "JNK-Deploy-Drupal-DEV-STAGE": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/00 deploy_branch_to_eng.*",
          "^NCSA/01 deploy_branch_to_dev.*",
          "^NCSA/02 deploy_branch_to_stage.*",
          "^NCSA/Turn on.*",
          "^NCSA/.*Test?",
          "^NCSA/01_dev_deploy",
          "^NCSA/02_stage_deploy",
          "^NCSA/00_eng_deploy",
          "^NCSA/04 deploy_branch_to_release",
          "^NCSA/Drupal_pr-build",
          "^NCSA/Force Gulp Build Dev or Stage",
          "^NCSA/QA_Sanity_Check"
      ],
      "group": ["JNK-Deploy-Drupal-DEV-STAGE"]
  ],
  "JNK-Production-Release-Manager": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/04 deploy_branch_to_release",
          "^NCSA/cms_release_test",
          "^NCSA/Drupal_Rollback_Production",
          "^NCSA/Release Lock.*",
          "^NCSA/Drupal_Rollback_Production"
      ],
      "group": ["JNK-Production-Release-Manager"]
  ],
  "JNK-Qas-content-sync": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Hudson.SystemRead",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Personal Server Content Sync - QA",
          "^NCSA/Tools - Clear Jom HUB Page Cache",
          "^NCSA/Project Drupal Etasks - STAGE",
          "^NCSA/Project Drupal Etasks - DEV"
      ],
      "group": ["JNK-Qas-content-sync"]
  ],
  "JNK-AWS-Rolo-Admin": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Deploy to Rolo",
          "^NCSA/Sync to Rolo"
      ],
      "group": ["JNK-AWS-Rolo-Admin"]
  ],
  "JNK-AWS-Sysadmin": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Tools.*",
          "^NCSA/Varnish Admin - Update default.vcl",
          "^NCSA/Varnish Admin - Run varnishadm command"
      ],
      "group": ["JNK-AWS-Sysadmin"]
  ],
  "JNK-Deploy-perlgem-users": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Tools - Deploy perlgem_users"
      ],
      "group": ["JNK-Deploy-perlgem-users"]
  ],
  "JNK-Ziggy-Admin": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Deploy to Ziggy",
          "^NCSA/Sync to Ziggy"
      ],
      "group": ["JNK-Ziggy-Admin"]
  ],
  "JNK-Commit-Log-Generator": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Tools - Generate Commit Log"
      ],
      "group": ["JNK-Commit-Log-Generator"]
  ],
  "JNK-Content-Sync": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/clinique_content_sync.*",
          "^NCSA/Genie Content Sync.*",
          "^NCSA/Estee Lauder Content Sync.*",
          "^NCSA/Jo Malone Content Sync.*",
          "^NCSA/LaMer Content Sync.*",
          "^NCSA/Bobbi Brown Content Sync.*",
          "^NCSA/origins content sync.*",
          "^NCSA/Maccosmetics Content Sync.*",
          "^NCSA/.*backup_database",
          "^NCSA/Training Server Code and Content Sync",
          "^NCSA/.*refresh?",
          "^NCSA/glamglow content sync",
          "^NCSA/Release Server Content Sync2",
          "^NCSA/Release Server Content Sync",
          "^NCSA/labseries_content_sync",
          "^NCSA/Shared Content Sync",
          "^NCSA/Release Server Content Sync -- ALL",
          "^NCSA/Bumble Content Sync",
          "^NCSA/darphin_content_sync"
      ],
      "group": ["JNK-Content-Sync"]
  ],
  "JNK-Engineer": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Personal Server Content Sync",
          "^NCSA/Clear Js Bundle Akamai",
          "^NCSA/Drupal Linter"
      ],
      "group": ["JNK-Engineer"]
  ],
  "JNK-Platform": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Personal Server Content Sync.*",
          "^NCSA/Personal Server Content Sync - Platform Team*",
          "^NCSA/Drupal Etask.*",
          "^NCSA/Drupal Schedule Publish Cron",
          "^NCSA/Tools - Personal Server - Add Database",
          "^NCSA/new_content_sync",
          "^NCSA/Selena 2020 Warmer",
          "^NCSA/Drupal SSR.*"
      ],
      "group": ["JNK-Platform"]
  ],
  "JNK-pull-and-restart-apache": [
      "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.View.Read"
      ],
      "pattern": [
          "^NCSA",
          "^NCSA/Tools - Pull and Restart Apache"
      ],
      "group": ["JNK-pull-and-restart-apache"]
  ]
]


PROJECT_STARDUST_DEFAULT_ROLES = [
  "JNK-AWS-Global-User-nonProdBuild": [
      "permissions": [
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay"
      ],
      "pattern": ['^((?!NCSA|production).)*$'],
      "group": ["JNK-AWS-Global-User"]
  ],
  "JNK-AWS-Global-User-readOnly": [
      "permissions": [
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read"
      ],
      "pattern": ['^(?!NCSA).*$'],
      "group": ["JNK-AWS-Global-User"]
  ],

]
PROJECT_STARDUST_DEVOPS_ROLE = [
  "JNK-AWS-DevOps" :[
     "permissions": [
          "hudson.model.Hudson.Read",
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.ExtendedRead",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay",
          "hudson.model.View.Read"
      ],
     "pattern": [
         "^[^NCSA/].*"
     ],
     "group": ["JNK-AWS-DevOps"]
  ]
]
PROJECT_STARDUST_PRODUCTION_ROLES = [
  "JNK-AWS-SD-Utils-Prod": [
      "permissions": [
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay"
      ],
      "pattern": [
          '^microservices-stardust-utils',
          '^microservices-stardust-utils/.*'
      ],
      "group": ["JNK-AWS-Sd-Utils-Prod-Deploy"]
  ],
  "JNK-AWS-SD-FE-Prod": [
      "permissions": [
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay"
      ],
      "pattern": [
          '^react-components',
          '^react-components/.*',
          '^microservices-panda',
          '^microservices-panda/.*'
      ],
      "group": ["JNK-AWS-Sd-Fe-Prod-Deploy"]
  ],
  "JNK-AWS-SD-BE-Prod": [
      "permissions": [
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay"
      ],
      "pattern": [
          '^microservices-stardust',
          '^microservices-stardust/.*'
      ],
      "group": ["JNK-AWS-Sd-Be-Prod-Deploy"]
  ],
  "JNK-AWS-SD-MA-Prod": [
      "permissions": [
          "hudson.model.Item.Build",
          "hudson.model.Item.Cancel",
          "hudson.model.Item.Discover",
          "hudson.model.Item.Read",
          "hudson.model.Item.Workspace",
          "hudson.model.Run.Replay"
      ],
      "pattern": [
          '^microservices-stardust-utils',
          '^microservices-stardust-utils/prodcat-orchestrator',
          '^microservices-stardust-utils/prodcat-orchestrator/.*-TRIGGER_IMPORT'
      ],
      "group": ["JNK-AWS-Sd-Ma-Prod"]
  ],
]


def addRoles(RoleBasedAuthorizationStrategy strategy, String roleType, HashMap roleInfoMap) {
    for (item in roleInfoMap) {
        addRole(strategy, roleType, item)
    }
}

def addRole(RoleBasedAuthorizationStrategy strategy, String roleType, Object roleInfo) {
    Set<Permission> permissionSet = new HashSet<Permission>()
    roleInfo.value.get("permissions").each { p ->
        def permission = Permission.fromId(p)
        if(permission != null){
            permissionSet.add(permission)
        } else {
            LOGGER.severe("${p} is not a valid permission ID (ignoring)")
        }
    }
    print(roleInfo.key + ' -> ' + roleInfo.value.get("pattern") + '\n')
    Role role
    if (roleType == RoleBasedAuthorizationStrategy.PROJECT) {
        role = new Role(roleInfo.key, roleInfo.value.get("pattern").join('|'), permissionSet)
    } else if (roleType == RoleBasedAuthorizationStrategy.GLOBAL) {
        role = new Role(roleInfo.key, permissionSet)
    } else {
        LOGGER.severe('Unknown role based authorization strategy: ' + roleType)
        System.exit(1)
    }
    roleBasedAuthenticationStrategy.addRole(roleType, role)

    roleInfo.value.get("group").each{ g ->
        LOGGER.info("Granting ${g} access to ${role.getName()} project role")
        roleBasedAuthenticationStrategy.assignRole(roleType, role, g)
    }
}

LOGGER.info('Running roles and permissions configuration script!')

// Set new authentication strategy
roleBasedAuthenticationStrategy = new RoleBasedAuthorizationStrategy()
Constructor[] constrs = Role.class.getConstructors()
    for (Constructor<?> c : constrs) {
        c.setAccessible(true)
}
Method assignRoleMethod = RoleBasedAuthorizationStrategy.class.getDeclaredMethod("assignRole", String.class, Role.class, String.class)
assignRoleMethod.setAccessible(true)



switch(JENKINS_URL) {
    case 'https://jenkins.esteeonline.com/':
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_ADMIN_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_POWERUSER_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_READONLY_ROLES)

        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_NCSA_DEFAULT_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_NCSA_ROLES)

        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEFAULT_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEVOPS_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_PRODUCTION_ROLES)

        break
    case 'https://jenkins.esteeonline.cn/':
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_ADMIN_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_POWERUSER_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_READONLY_ROLES)

        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEFAULT_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEVOPS_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_PRODUCTION_ROLES)

        break
    case 'https://jenkins-staging.elco-cloud.cn/':
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_ADMIN_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_POWERUSER_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_READONLY_ROLES)

        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEFAULT_ROLES)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_DEVOPS_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.PROJECT, PROJECT_STARDUST_PRODUCTION_ROLES)

        break
    case 'https://jenkins-migration.elco.cloud/':
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_ADMIN_ROLE)
        addRoles(roleBasedAuthenticationStrategy, RoleBasedAuthorizationStrategy.GLOBAL, GLOBAL_POWERUSER_ROLES)
        break
    default:
        LOGGER.info('Skipping Jenkins roles configuration for unknown deployments (JENKINS_URL=' + JENKINS_URL + ')!')
        return
}

JENKINS_INSTANCE.setAuthorizationStrategy(roleBasedAuthenticationStrategy)
JENKINS_INSTANCE.save()