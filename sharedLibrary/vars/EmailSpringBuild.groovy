def call(body) {

    pipeline {
        agent any
        stages {
            stage('Initialize parameters') {
                steps {
                    script {
                        def branchNames = GetBranches()
                        properties([
                            parameters ([
                                choice(
                                    choices: branchNames,
                                    name: 'BranchChoice'
                                )
                            ])
                        ])
                    }
                }
            }
            stage('checkout') {
                steps {
                    git branch:params.BranchChoice, url: 'https://github.com/Sebziel/EmailSpring.git'
                }
            }
            stage('build') {
                steps {
                    sh 'mvn clean package'
                }
            }
            stage('archive Artifacts') {
                steps {
                    archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
                }
            }
            stage('build and push container'){
                steps{
                    script{
                        def accountId = sh(script: "aws sts get-caller-identity --query Account --output text", returnStdout: true).trim()
                        def imageName = "${accountId}.dkr.ecr.us-east-1.amazonaws.com/email-spring:${env.BUILD_ID}"
                        sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${accountId}.dkr.ecr.us-east-1.amazonaws.com"
                        docker.build(imageName)
                        docker.image(imageName).push()                       
                    }
                }
                
            }
        }
    }
}