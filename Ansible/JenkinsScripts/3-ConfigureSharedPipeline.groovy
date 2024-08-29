import jenkins.model.*
import org.jenkinsci.plugins.workflow.libs.*
import jenkins.plugins.git.GitSCMSource

// Define library details
def libraryName = 'TF-Ansible-Jenkins'
def libraryRepoUrl = 'https://github.com/Sebziel/TF-AWS-Ansible-Jenkins.git'
def libraryDefaultVersion = 'main'

// Get Jenkins instance
def jenkins = Jenkins.getInstance()

// Get GlobalLibraries configuration
def globalLibraries = GlobalLibraries.get()

// Create a new library configuration
def scmSource = new GitSCMSource(libraryRepoUrl)
def scmRetriever = new SCMSourceRetriever(scmSource)
def libraryConfiguration = new LibraryConfiguration(libraryName, scmRetriever)
libraryConfiguration.setDefaultVersion(libraryDefaultVersion)

// Create a new list and add existing libraries and the new library configuration
def libraries = new ArrayList<>(globalLibraries.getLibraries())
libraries.add(libraryConfiguration)

// Save the global libraries configuration
globalLibraries.setLibraries(libraries)
globalLibraries.save()

println "Global trusted pipeline library '${libraryName}' set up successfully."