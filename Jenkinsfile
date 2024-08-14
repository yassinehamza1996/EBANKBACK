node{
    stage('Clone'){
        git 'https://github.com/BrodyGaudel/E-BANK-BACKEND.git'
    }
    stage('Build'){
        bat 'mvn clean install'
    }
    stage('Run'){
        bat 'mvn spring-boot:run'
    }
}
