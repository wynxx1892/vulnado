
def JAVA_FILES = ''
def JOB_ID = ''
def OUTPUT_URIS = ''
def LLM = ''
def LANGUAGE = ''
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Authenticate with Keycloak') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'WYNXX_CREDENTIALS', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    script {
                        def response = sh(script: '''curl -vk -s -X POST 'https://auth.aiimpact.qa.az.gcp-gft.cloud/realms/ai-impact/protocol/openid-connect/token' \
                        -H 'Content-Type: application/x-www-form-urlencoded' \
                        -d 'client_id=ai-impact-client' \
                        -d "username=$USER" \
                        -d "password=$PASS" \
                        -d "scope=openid" \
                        -d "grant_type=password"''', returnStdout: true).trim()
                        def token = sh(script: """#!/bin/bash
                            set +x
                            echo '${response}' | jq -r '.access_token'""", returnStdout: true).trim()
                        if (!token || token == 'null') {
                            error "Failed to obtain access token: ${response}"
                        }
                        writeFile file: 'access.token', text: token
                    }
                }
            }
        }
        stage('Create Classes List') {
            steps {
                script {
                    def javaFiles = sh(script: "find ${WORKSPACE}/src/main/java/com/scalesec/vulnado -type f -name '*.java'", returnStdout: true).trim()
                    JAVA_FILES = javaFiles
                }
            }
        }
        post {
        always {
            echo 'Pipeline finished.'
        }
    }  
    }
}