node {
  def build_result = false
  stage('checkout') {
    mattermostSend(color: "#2A42EE", message: "BUILD STARTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
    checkout scm
  }

  stage('build') {
    try {
        sh './gradlew clean build -x test'
    } catch (e) {
        mattermostSend(color: "#2A42EE", message: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
    }
  }

  stage('test') {
    try {
        sh './gradlew test'
    } catch (e) {
        mattermostSend(color: "#2A42EE", message: "TEST FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
    }
  }
}
