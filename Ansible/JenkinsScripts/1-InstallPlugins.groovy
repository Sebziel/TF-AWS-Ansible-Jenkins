import jenkins.model.*
import hudson.PluginManager
import hudson.model.UpdateCenter

def plugins = [
    'git',
    'credentials-binding',
    'pipeline-github-lib',
    'pipeline-stage-view',
    'pipeline-model-definition',
    'credentials',
    'ssh-slaves',
    'matrix-auth',
    'job-dsl',
    'email-ext',
    'github',
    'github-branch-source'
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
            def installFuture = plugin.deploy(true)
            installFuture.get()
            println("Plugin ${pluginName} installed successfully")
        } else {
            println("Plugin ${pluginName} not found in update center")
        }
    } else {
        println("Plugin ${pluginName} is already installed")
    }
}

instance.save()