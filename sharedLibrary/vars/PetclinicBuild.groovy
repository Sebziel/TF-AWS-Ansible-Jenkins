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
                    git branch:params.BranchChoice, url: 'https://github.com/Sebziel/spring-petclinic.git'
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
        }
    }
}