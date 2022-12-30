node {
  def build_result = false
  stage('checkout') {
    mattermostSend color: '#2A42EE', message: 'BUILD STARTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)'
    checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/kenux196/travelog.git']]])
  }

  stage('build') {
    try {
        sh './gradlew clean build -x test'
    } catch (e) {
      mattermostSend color: 'danger', message: 'FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)'
    }
  }

  stage('test') {
    try {
        sh './gradlew test'
    } catch (e) {
      mattermostSend color: 'danger', message: 'FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)'
    }
  }
}
