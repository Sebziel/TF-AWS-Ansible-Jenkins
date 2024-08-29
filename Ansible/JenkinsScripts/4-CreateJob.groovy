import jenkins.model.*
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import com.coravy.hudson.plugins.github.GithubProjectProperty


def jobName = 'example-pipeline-job-2'
def githubRepoUrl = 'https://github.com/your-github-username-or-organization/your-repository-name'
def githubProjectProperty = new GithubProjectProperty(githubRepoUrl)

def jenkins = Jenkins.instance
def job = jenkins.createProject(WorkflowJob, jobName)
def pipelineScript = '''
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo 'Hello, World!'
            }
        }
    }
}
'''.stripIndent()
def flowDefinition = new CpsFlowDefinition(pipelineScript, true)
job.setDefinition(flowDefinition)
job.setDescription('This is a pipeline job.')
job.addProperty(githubProjectProperty)
job.save()
println("Created pipeline job")