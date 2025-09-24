
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
        stage('Send to API (TestCreator)') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    def curlArgs = ''
                    def javaFiles = JAVA_FILES.split("\\s+") as List
                    echo "Parsed javaFiles: ${javaFiles}"

                    javaFiles.each { file ->
                        def className = file.tokenize('/')[-1].replace('.java','')
                        def testFile = "src/test/java/com/scalesec/vulnado/${className}Tests.java"

                        curlArgs += " --form \"files=@${file}\""
                        if (fileExists(testFile)) {
                            curlArgs += " --form \"ExistingTests=@${testFile}\""
                        }
                    }

                    echo "Generated curlArgs: ${curlArgs}"

                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        def response = sh(script: """#!/bin/bash
                            set +x
                            curl --location 'https://api.aiimpact.qa.az.gcp-gft.cloud/ai/test' \
                                --header "Authorization: Bearer ${ACCESS_TOKEN}" \
                                ${curlArgs} \
                                --form "RunName=GenerateTests" \
                                --form "jobName=DemoTestCreator" \
                                --form "SearchPattern=*.java" \
                                --form "TargetExtension=java" \
                                --form "PromptId=TestCreator__CreateUnitTests_V1" \
                                --form "SourceCodeLanguage=Java" \
                                --form "TestType=Unit" \
                                --form "TestingFrameworks=MSTEST,MOQ" \
                                --form "Llm=${LLM}" \
                                --form "AdditionalInstructions=Generate only the source code, without any extra information"
                        """, returnStdout: true).trim()
                        echo "API response: ${response}"
                        JOB_ID = response
                    }
                }
            }
        }


        stage('Monitor Test Job') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    def status = 'Pending'
                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        def res = sh(script: """#!/bin/bash
                        set +x
                        curl --location https://api.aiimpact.qa.az.gcp-gft.cloud/ai/jobs/${JOB_ID}/status \
                        --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                        echo "res: $res"
                        while (!(status in ['Completed', 'CompletedWithErrors'])) {
                            res = sh(script: """#!/bin/bash
                            set +x
                            curl --location https://api.aiimpact.qa.az.gcp-gft.cloud/ai/jobs/${JOB_ID}/status \
                                --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                            status = sh(script: "echo '${res}' | jq -r '.status'", returnStdout: true).trim()
                            echo "Current status: ${status}"
                            sleep time: 10, unit: 'SECONDS'
                        }
                        OUTPUT_URIS = sh(script: "echo '${res}' | jq -r '.results[].output[].uri'", returnStdout: true).trim()
                    }  
                }
            }
        }

        stage('Save Generated Tests') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        OUTPUT_URIS.split('\n').each { uri ->
                            def className = uri.tokenize('/')[-1].replace('.java','')
                            def content = sh(script: """#!/bin/bash
                            set +x
                            curl --location https://api.aiimpact.qa.az.gcp-gft.cloud${uri} \
                                --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                            def testFile = "src/test/java/com/scalesec/vulnado/${className}Tests.java"
                            writeFile file: testFile, text: content
                        }
                    }
                }
            }
        }

    }
        stage('Prepare and Send to API (DocCreator)') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    def curlArgs = ''
                    def javaFiles = JAVA_FILES.split("\\s+") as List
                    echo "Parsed javaFiles: ${javaFiles}"

                    javaFiles.each { file ->
                        def className = file.tokenize('/')[-1].replace('.java','')
                        curlArgs += " --form \"files=@${file}\""
                    }

                    echo "Generated curlArgs: ${curlArgs}"

                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        def response = sh(script: """#!/bin/bash
                            set +x
                            curl --location 'https://api.aiimpact.qa.az.gcp-gft.cloud/ai/document' \
                                --header "Authorization: Bearer ${ACCESS_TOKEN}" \
                                ${curlArgs} \
                                --form "jobName=DemoDocCreator" \
                                --form "DocumentationFormat=markdown" \
                                --form "DiagramFormat=Mermaid" \
                                --form "SourceCodeLanguage=Java" \
                                --form "DocumentationAudience=Developer" \
                                --form "PromptId=DocCreator__DocumentCode_V3" \
                                --form "TargetExtension=md" \
                                --form "Llm=${LLM}" \
                                --form "AdditionalInstructions=Generate all answers in ${LANGUAGE} and If the code have vulnerabilities, describe all in a new vulnerabilities section"
                                """, returnStdout: true).trim()
                        echo "API response: ${response}"
                        JOB_ID = response
                    } 
                }
            }
        }

        stage('Monitor Doc Job') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    def status = 'Pending'
                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        def res = sh(script: """#!/bin/bash 
                        set +x
                        curl --location https://api.aiimpact.qa.az.gcp-gft.cloud/ai/jobs/${JOB_ID}/status \
                                --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                        echo "res: $res"
                        while (!(status in ['Completed', 'CompletedWithErrors'])) {
                            res = sh(script: """#!/bin/bash
                            set +x
                            curl --location http://api.gftaiimpact.local:8080/ai/jobs/${JOB_ID}/status \
                                --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                            status = sh(script: "echo '${res}' | jq -r '.status'", returnStdout: true).trim()
                            echo "Current status: ${status}"
                            sleep time: 10, unit: 'SECONDS'
                        }
                        OUTPUT_URIS = sh(script: "echo '${res}' | jq -r '.results[].output[].uri'", returnStdout: true).trim()
                    }
                       
                }
            }
        }

        stage('Save Generated Docs') {
            steps {
                script {
                    def token = readFile('access.token').trim()
                    withEnv(["ACCESS_TOKEN=${token}"]) {
                        OUTPUT_URIS.split('\n').each { uri ->
                            def className = uri.tokenize('/')[-1].replace('.java','')
                            def content = sh(script: """#!/bin/bash
                            set +x
                            curl --location https://api.aiimpact.qa.az.gcp-gft.cloud${uri} \
                                --header 'Authorization: Bearer ${ACCESS_TOKEN}'""", returnStdout: true).trim()
                            def docFile = "wiki/src/main/java/com/scalesec/vulnado/${className}Docs.md"
                            writeFile file: docFile, text: content
                        }
                    }
                }
            }
        }
    

    post {
    always {
        echo 'Pipeline finished.'
    }
    }  
    
}