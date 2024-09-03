import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import com.coravy.hudson.plugins.github.GithubProjectProperty


def jobName = 'EmailSpringBuild'
def githubRepoUrl = 'https://github.com/Sebziel/EmailSpring.git'
def githubProjectProperty = new GithubProjectProperty(githubRepoUrl)

def jenkins = Jenkins.instance
def job = jenkins.createProject(WorkflowJob, jobName)
def pipelineScript = '''
@Library('TF-Ansible-Jenkins') _

// Call the hello.groovy pipeline
EmailSpringBuild()
'''.stripIndent()
def flowDefinition = new CpsFlowDefinition(pipelineScript, true)
job.setDefinition(flowDefinition)
job.setDescription('This is a pipeline job.')
job.addProperty(githubProjectProperty)
job.save()
println("Created pipeline job")