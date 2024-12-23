pipeline{
    environment{
        BACKEND_IMAGE_NAME = "yuktax/backend"
        FRONTEND_IMAGE_NAME = "yuktax/frontend"
    }
    agent any
    stages{
        stage('Stage 1: Git Clone'){
            steps{
                git branch: 'master',
                url:'https://github.com/yuktaX/SPE_Final_Project'
            }
        }
        stage('Stage 2: Setup Backend'){
            steps{
                sh '''
                    cd backend
                    mvn clean install
                '''
            }
        }
        
        stage('Stage 3: Test Backend'){
            steps{
                sh '''
                    cd backend
                    mvn test
                '''
            }
        }
        
        stage('Stage 4: Build and Push Backend Docker Image') {
            steps {
                script {
                    def backendImage = docker.build(env.BACKEND_IMAGE_NAME, './backend')
                    docker.withRegistry('', 'DockerHubCred') {
                        backendImage.push('latest')
                    }
                }
            }
        }
        
        stage('Stage 5: Build and Push Frontend Docker Image') {
            steps {
                script {
                    def frontendImage = docker.build(env.FRONTEND_IMAGE_NAME, './frontend')
                    docker.withRegistry('', 'DockerHubCred') {
                        frontendImage.push('latest')
                    }
                }
            }
        }
        
        stage('Stage 6: Clean Docker Images') {
            steps {
                script {
                    sh 'docker container prune -f'
                    sh 'docker image prune -f'
                }
            }
        }
        
        stage('Stage 7: Ansible Deployment')
        {
            steps
            {   
                ansiblePlaybook becomeUser: null,
                colorized: true,
                credentialsId: 'localhost',
                disableHostKeyChecking: true,
                installation: 'Ansible',
                inventory: 'Deployment/inventory',
                playbook: 'Deployment/deploy.yml',
                sudoUser: null
            }
        }
    }
}