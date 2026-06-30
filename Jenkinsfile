pipeline {

    agent any

    stages {

        stage('Maven Build') {

            steps {

                bat 'mvn clean test'
            }
        }

        stage('Docker Build') {

            steps {

                bat 'docker build -t banking-api-framework .'
            }
        }

        stage('Docker Execute') {

            steps {

                bat 'docker run loan-management-api-framework'
            }
        }
    }
}