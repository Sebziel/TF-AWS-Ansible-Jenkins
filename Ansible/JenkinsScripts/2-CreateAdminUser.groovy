import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Set up the Security Realm
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin", "admin_password")
instance.setSecurityRealm(hudsonRealm)

// Set up the Authorization Strategy (Matrix-based)
def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin")
instance.setAuthorizationStrategy(strategy)

println("Created Admin user with admin permissions")
instance.save()
