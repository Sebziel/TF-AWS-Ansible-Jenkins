import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule

// Define the username, password, and full name for the new admin user
def username = "admin"
def password = "admin_password"
def fullName = "Admin User"

// Get the Jenkins instance
def instance = Jenkins.getInstance()

// Get the security realm
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(username, password)
instance.setSecurityRealm(hudsonRealm)

// Get the authorization strategy
def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, username)
instance.setAuthorizationStrategy(strategy)

// Save the state
instance.save()

// Disable the CLI over Remoting
Jenkins.instance.getDescriptor("jenkins.CLI").get().setEnabled(false)

// Enable the Admin Whitelist rule
Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

println "Admin user '${username}' created successfully."