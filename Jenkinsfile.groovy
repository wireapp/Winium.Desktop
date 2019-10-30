node('node130') {
    
    properties([
        buildDiscarder(logRotator(daysToKeepStr: '30', numToKeepStr: '14'))
    ])
    
    def jenkinsbot_secret = ""
    withCredentials([string(credentialsId: "${params.JENKINSBOT_SECRET}", variable: 'JENKINSBOT_SECRET')]) {
        jenkinsbot_secret = env.JENKINSBOT_SECRET
    }
    
    stage('Checkout') {
        git branch: "${params.branch}", url: 'https://github.com/wireapp/Winium.Desktop.git'
    }
    
    stage('Download NuGet') {
        bat """if not exist nuget.exe (
@\"%SystemRoot%\\System32\\WindowsPowerShell\\v1.0\\powershell.exe\" ^
-NoProfile -InputFormat None -ExecutionPolicy Bypass ^
-Command \"(new-object System.Net.WebClient).DownloadFile('https://dist.nuget.org/win-x86-commandline/latest/nuget.exe', 'nuget.exe')\"
) else (
  echo \"nuget.exe already exists.\"
)"""
    }
    
    stage('Build') {
        bat """cd src
..\\nuget.exe restore Winium.sln
\"${tool 'MSBuild'}\" Winium.sln /p:Configuration=Release /p:Platform=\"Any CPU\" /p:ProductVersion=1.0.0.${env.BUILD_NUMBER}"""
    }
    
    stage('Archive') {
        archiveArtifacts 'src/Winium.Desktop.Driver/bin/Release/Merge/**'
    }
}
