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
        stage('Stage 2: Test Backend'){
            steps{
                sh '''
                    cd backend
                    mvn clean install
                '''
            }
        }
        
        stage('Stage 3: Build and Push Backend Docker Image') {
            steps {
                script {
                    def backendImage = docker.build(env.BACKEND_IMAGE_NAME, './backend')
                    docker.withRegistry('', 'DockerHubCred') {
                        backendImage.push('latest')
                    }
                }
            }
        }
        
        stage('Stage 4: Build and Push Frontend Docker Image') {
            steps {
                script {
                    def frontendImage = docker.build(env.FRONTEND_IMAGE_NAME, './frontend')
                    docker.withRegistry('', 'DockerHubCred') {
                        frontendImage.push('latest')
                    }
                }
            }
        }

                stage('Stage 5: Apply Kubernetes Manifests') {
            steps {
                script {
                    // Apply backend deployment and service
                    sh 'kubectl apply -f backend-deployment.yaml'
                    sh 'kubectl apply -f backend-service.yaml'

                    // Apply frontend deployment and service
                    sh 'kubectl apply -f frontend-deployment.yaml'
                    sh 'kubectl apply -f frontend-service.yaml'

                    // Apply Horizontal Pod Autoscaler
                    sh 'kubectl apply -f frontend-hpa.yaml'
                }
            }
        }

        stage('Stage 6: Wait for Frontend Pod to be Ready') {
            steps {
                script {
                    // Wait for frontend pod to be ready
                    sh '''
                        kubectl wait --for=condition=ready pod -l app=frontend --timeout=600s
                    '''
                }
            }
        }

        stage('Stage 7: Clean Docker Images') {
            steps {
                script {
                    sh 'docker container prune -f'
                    sh 'docker image prune -f'
                }
            }
        }

        stage('Stage 8: Verify Kubernetes Pods and Services') {
            steps {
                script {
                    // Check status of the pods to verify they are running
                    sh 'kubectl get pods'
                    
                    // Check the status of services to ensure frontend is accessible
                    sh 'kubectl get services'
                }
            }
        }
    }
}
