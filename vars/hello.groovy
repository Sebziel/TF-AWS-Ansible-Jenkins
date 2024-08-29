def call(body) {
    pipeline {
        agent any
        stages {
            stage('Initialize parameters') {
                steps {
                    sh "echo 'test'"
                }
            }
        }
    }
}