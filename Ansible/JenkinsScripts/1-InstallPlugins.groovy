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

// Save the current state to disk
instance.save()
