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
                                string(
                                    defaultValue: 'StringTest',
                                    name: 'TestStringParameter'
                                ),
                                choice(
                                    choices: branchNames,
                                    name: 'BranchChoice'
                                )
                            ])
                        ])
                    }
                }
            }
            stage('ParamTest') {
                steps {
                    echo params.TestStringParameter
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
        }
    }
}