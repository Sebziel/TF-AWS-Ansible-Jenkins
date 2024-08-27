import jenkins.model.*
import hudson.PluginManager
import hudson.model.UpdateCenter

def plugins = [
    'git',
    'credentials-binding',
    'pipeline-github-lib',
    'pipeline-stage-view',
    'credentials',
    'ssh-slaves',
    'matrix-auth',
    'job-dsl',
    'email-ext',
    'ldap'
]

// Get the Jenkins instance
def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()

plugins.each { pluginName ->
    if (!pm.getPlugin(pluginName)) {
        println("Installing plugin: ${pluginName}")
        def plugin = uc.getPlugin(pluginName)
        if (plugin) {
            plugin.deploy(true)
        } else {
            println("Plugin ${pluginName} not found in update center")
        }
    } else {
        println("Plugin ${pluginName} is already installed")
    }
}

def isPluginInstalled(pm) {
    return pm.getPlugin("matrix-auth")
}

int maxRetries = 10
int retries = 0

while (retries < maxRetries) {
    if (isPluginInstalled(pm)) {
        println "Plugin is installed. Proceeding to the next part of the script."
        break
    } else {
        println "Plugin is not installed. Sleeping for 6 second..."
        sleep(5000)
        retries++
    }
}

if (retries == maxRetries) {
    println "Plugin is still not installed after 10 retries. Exiting script."
}


instance.save()