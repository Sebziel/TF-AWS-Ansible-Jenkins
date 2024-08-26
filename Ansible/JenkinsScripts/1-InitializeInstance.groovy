import jenkins.model.*
import jenkins.install.*

println("Running init script to set install state")

def instance = Jenkins.getInstance()
instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)
instance.save()

println("Install state set to INITIAL_SETUP_COMPLETED")