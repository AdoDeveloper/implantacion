pipeline {
    agent any

    environment {
        CHROME_DRIVER_PATH = '/ruta/al/chromedriver'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'git-credentials-id', url: 'https://github.com/tuusuario/mi-aplicacion-springboot.git'
            }
        }
        stage('Compilar y Construir Imagen Docker') {
            steps {
                script {
                    sh 'docker build -t mi_aplicacion_app .'
                }
            }
        }
        stage('Pruebas Unitarias') {
            steps {
                sh 'mvn test'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Análisis Estático') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Pruebas de Integración') {
            steps {
                sh 'mvn verify -DskipUnitTests=false -Dtest=*IntegrationTest'
                junit '**/target/failsafe-reports/*.xml'
            }
        }
        stage('Construir y Publicar Imagen Docker') {
            steps {
                script {
                    sh 'docker build -t mi_aplicacion_app .'
                    sh 'docker tag mi_aplicacion_app tuusuario/mi_aplicacion_app:latest'
                    sh 'docker push tuusuario/mi_aplicacion_app:latest'
                }
            }
        }
        stage('Pruebas de UI') {
            steps {
                script {
                    // Iniciar los contenedores
                    sh 'docker-compose up -d'
                    sleep 20 // Esperar a que la aplicación y la base de datos inicien

                    // Ejecutar pruebas de UI
                    sh 'mvn test -Dtest=*UITest -Dwebdriver.chrome.driver=${CHROME_DRIVER_PATH}'
                }
                junit '**/target/surefire-reports/*.xml'
            }
            post {
                always {
                    // Detener los contenedores
                    sh 'docker-compose down'
                }
            }
        }
        stage('Pruebas de Rendimiento') {
            steps {
                sh 'jmeter -n -t performance/test.jmx -l performance/results.jtl'
                perfReport sourceDataFiles: 'performance/results.jtl'
            }
        }
        stage('Despliegue en Staging') {
            steps {
                sh 'docker-compose -f docker-compose.yml -f docker-compose.staging.yml up -d'
            }
        }
        stage('Pruebas de Aceptación') {
            steps {
                sh 'mvn verify -Dcucumber.options="src/test/resources/features"'
                junit '**/target/cucumber-reports/*.xml'
            }
        }
        stage('Despliegue a Producción') {
            when {
                expression { currentBuild.result == 'SUCCESS' }
            }
            steps {
                sh 'docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d'
            }
        }
    }
    post {
        success {
            slackSend(channel: '#deployments', message: "✅ Build exitoso: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
        failure {
            slackSend(channel: '#deployments', message: "❌ Build fallido: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
        always {
            cleanWs()
        }
    }
}