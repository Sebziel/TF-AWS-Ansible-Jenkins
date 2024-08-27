import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()

def isPluginInstalled(pm) {
    return pm.getPlugin("matrix-auth")
}

int maxRetries = 10
int retries = 0

while (retries < maxRetries) {
    if (isPluginInstalled(pm)) {
        println "Plugin is installed. Proceeding to the next part of the script."
        def hudsonRealm = new HudsonPrivateSecurityRealm(false)
        hudsonRealm.createAccount("admin", "admin_password")
        instance.setSecurityRealm(hudsonRealm)
        
        def strategy = new GlobalMatrixAuthorizationStrategy()
        strategy.add(Jenkins.ADMINISTER, "admin")
        instance.setAuthorizationStrategy(strategy)
        
        println("Created Admin user with admin permissions")
        instance.save()

        break
    } else {
        println "Plugin is not installed. Sleeping for 1 second..."
        sleep(5000)
        retries++
    }
}

if (retries == maxRetries) {
    println "Plugin is still not installed after 10 retries. Exiting script."
}