 @Library ('sama_gp') _  // charger plugins depuit git
 pipeline {
    agent any
    parameters {
            string(name: 'BRANCH_NAME', defaultValue: 'develop', description: 'Git branch to build')
            choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
            booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests before deploying')
        }
 //   environment {
   //     DOCKER_IMAGE = "samagp-annonce-api-ms"
  //      DOCKER_REGISTRY = "samagp"
  //      CONTAINER_NAME = "Sama-GP"
  //  }
 environment {
        REMOTE_HOST = "192.168.56.11"
        REMOTE_USER = "vagrant"
        REMOTE_DIR = "/home/vagrant/deploy/samagp5"
        JAR_FILE = "samagp-api-ms-0.0.1-SNAPSHOT.jar"
        SSH_CREDENTIAL_ID = "vargrant-public-key"  // Replace with the correct credential ID
        MASTER_SSH_CREDENTIAL_ID = "vargrant-public-key"  // Replace with the correct credential ID
    }

    stages {
        stage('Load Env Properties'){
               steps{
                   script{
                      def envVars =  EnvVariablesLoader()
                      envVars.each { key, value ->
                            env[key] = value
                      }
                       }
               }
        }
        stage('Use Variables') {
                    steps {
                        script {
                            echo "MY_VAR = ${env.MASTER_CREDENTIAL_ID}"
                            echo "API_KEY = ${env.GIT_CREDENTIAL_ID}"
                            echo "DATABASE_URL = ${env.ANNONCE_MS_GIT_URL}"
                        }
                    }
                }
        stage('Run Groovy from git library'){
               steps{
                      script{
                       buildDockerImage('sama-gp-annonce-image', 'Dockerfile')
                    }
                 }
        }
        stage('Checkout Code') {
            steps {
                git branch: "${params.BRANCH_NAME}",
                    credentialsId: '9c33aab2-46ee-4c99-ada0-92dbe6f31151',
                    url: 'https://github.com/sama-gp/samagp-api-ms.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            when {
               expression { return params.RUN_TESTS }
            }
            steps {
                sh 'mvn test'
            }
        }

        stage('k8s Deployment on workers') {
                     steps {
                         script {
                        sshagent(['k8s-master-ssh-credential-id']) {
                        sh '''
                        ssh -o StrictHostKeyChecking=no $K8S_MASTER << EOF
                        kubectl apply -f /path/to/k8s/
                        kubectl get pods -o wide
                        EOF
                        '''
                }
                         }
                     }
                }
      /*  stage('Deploy on workers') {
              steps {
                    script {
                        deployOnWorker()
                             }
                    }
        }*/
        /*stage('Check K8s Connectivity') {
                     steps {
                       sh '''
                         kubectl apply -f deployment.yaml
                         kubectl apply -f service.yaml
                       '''
                     }
                 }*/
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            echo 'Deployment Successful! 🎉'
        }
        failure {
            echo 'Builds Failed! ❌'
        }
    }
}