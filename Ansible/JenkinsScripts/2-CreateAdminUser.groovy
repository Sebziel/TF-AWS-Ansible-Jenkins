import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","admin_password")
instance.setSecurityRealm(hudsonRealm)
println("Created Admin user")
instance.save()