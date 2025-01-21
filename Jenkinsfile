pipeline {
    agent {
        kubernetes {
            cloud "kubernetes"
            inheritFrom "jenkins-agent-jdk17"
        }
    }

	environment {
	    APP_CODE = 'https://gitee.com/yaohaihan/moodiary-developer-heyuan.git'
	    CODE_BRANCH = 'DeployTest'

	    REGISTRY = '192.168.235.192:30002'
        DOCKERHUB_NAMESPACE = 'service_images'
        NAMESPACE= 'base-cluster'
        HARBOR_SECRET= 'harbor'

		MODULES = 'User-service,Post-service,PayOrder-Service,Product-service,Friendship-service,Record-service,Transaction-service,Address-service,Md-gateway'
	}

    stages{

        stage('Checkout Code') {
            steps {
                git branch: "$CODE_BRANCH", credentialsId: '28731b6e-9cf4-4379-ab07-ea50fb7cdddf', url: "$APP_CODE"
            }
        }


		stage('Determine Changes') {
            steps {
                script {
                    def changedFiles = sh(script: "git diff --name-only HEAD~1 HEAD", returnStdout: true).trim()

                    echo "Changed files: ${changedFiles}"

                    env.BUILD_COMMON = changedFiles.contains('Md-common')
                    env.BUILD_API = changedFiles.contains('Md-api')

                    echo "BUILD_COMMON: ${env.BUILD_COMMON}"
                    echo "BUILD_API: ${env.BUILD_API}"

                    def modules = env.MODULES.split(',')
                    for (module in modules) {
                        def moduleEnvVar = module.toUpperCase().replace('-', '_')
                        env["BUILD_${moduleEnvVar}"] = changedFiles.contains(module).toString()
                        echo "BUILD_${moduleEnvVar}: ${env["BUILD_${moduleEnvVar}"]}"

                        if (changedFiles.contains(module)) {
                            echo "Module ${module} has changed. Triggering corresponding pipeline..."
                            build job: module, wait: false
                        }
                    }
                }
            }
        }
    }
}
