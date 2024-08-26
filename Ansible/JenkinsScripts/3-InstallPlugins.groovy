import jenkins.model.*
import hudson.PluginManager
import hudson.model.UpdateCenter
import hudson.model.UpdateCenter.UpdateCenterJob
import java.util.logging.Logger

def logger = Logger.getLogger("")

// Define the plugin name
def pluginName = "matrix-auth"

// Get the Jenkins instance
def instance = Jenkins.getInstance()

// Get the Update Center
def updateCenter = instance.getUpdateCenter()

// Check if the plugin is already installed
def plugin = instance.getPluginManager().getPlugin(pluginName)
if (plugin != null) {
    logger.info("Plugin '${pluginName}' is already installed.")
} else {
    // Install the plugin
    logger.info("Installing plugin '${pluginName}'...")
    def pluginInstallJob = updateCenter.getPlugin(pluginName).deploy()

    // Wait for the installation to complete
    pluginInstallJob.get()

    // Check if the plugin was installed successfully
    if (pluginInstallJob.getStatus().isSuccess()) {
        logger.info("Plugin '${pluginName}' installed successfully.")
    } else {
        logger.severe("Failed to install plugin '${pluginName}'.")
    }
}

// Save the Jenkins instance state
instance.save()