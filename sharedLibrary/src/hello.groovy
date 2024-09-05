def call(body) {
    pipeline {
        agent any
        stages {
            stage('Initialize parameters') {
                steps {
                    echo "Hello From shared Library."
                }
            }
        }
    }
}