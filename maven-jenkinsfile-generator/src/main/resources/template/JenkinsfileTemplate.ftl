/**
* Author: Marcus
* Version: 1.0.0
* Description:jenkins DevOps部署脚本模板
* Date：2020-12-17
*/
pipeline {
agent any
stages {
stage('env init') {
steps {
script {
<#if sit??>
    getSitIps()
</#if>
<#if uat??>
    getUatIps()
</#if>
<#if pre??>
    getPreIps()
</#if>
<#if pro??>
    getProIps()
</#if>
}
}
}
stage('maven package') {
steps {
sh 'mvn clean package -Dmaven.test.skip=true'
archiveArtifacts artifacts: "**/target/${r'${appName}'}", fingerprint: true
}
}

<#if sit??>
    stage('sit') {
    parallel {
    stage('deploy') {
    when {
    branch "${r'${sitBranch}'}"
    }
    steps {
    withCredentials(bindings: [usernamePassword(credentialsId: "${r'${sitCredentialsId}'}", passwordVariable: 'password', usernameVariable: 'userName')]) {
    //echo sh(returnStdout:true,script:'env')
    installJavaRuntime("sit")
    deploy("${r'${sitSpringProfilesActive}'}")
    }
    }
    }
    }
    }
</#if>

<#if uat??>
    stage('uat') {
    parallel {
    stage('deploy') {
    when {
    branch "${r'${uatBranch}'}"
    }
    steps {
    withCredentials(bindings: [usernamePassword(credentialsId: "${uatCredentialsId}", passwordVariable: 'password', usernameVariable: 'userName')]) {
    installJavaRuntime("uat")
    deploy("${uatSpringProfilesActive}")
    }

    }
    }
    stage('eolinker') {
    when {
    branch "${r'${uatBranch}'}"
    }
    steps {
    sleep 20
    eolinkerTestCase()
    }
    }
    }
    }
</#if>

<#if uat??>
    stage('pre') {
    parallel {
    stage('deploy') {
    when {
    branch "${preBranch}"
    }
    steps {
    withCredentials(bindings: [usernamePassword(credentialsId: "${preCredentialsId}", passwordVariable: 'password', usernameVariable: 'userName')]) {
    installJavaRuntime("pre")
    deploy("${preSpringProfilesActive}")
    }
    }
    }

    }
    }
</#if>

<#if pro??>
    stage('pro') {
    parallel {
    stage('deploy') {
    when {
    branch "${proBranch}"
    }
    steps {
    withCredentials(bindings: [usernamePassword(credentialsId: "${proCredentialsId}", passwordVariable: 'password', usernameVariable: 'userName')]) {
    installJavaRuntime("pro")
    deploy("${proSpringProfilesActive}")
    }
    }
    }
    }
    }
</#if>

stage('success') {
steps {
echo 'success build'
}
}

}
post {
success {
dingtalk(
robot: "${r'${dingdingId}'}",
type: 'LINK',
atAll: false,
title: "成功",
messageUrl: "${r'${env.BUILD_URL}'}",
picUrl: 'http://sortserverfat.yto56.com.cn:8080/success.png',
text: ["项目：${r'${env.appName}'}\n分支：${r'${env.BRANCH_NAME}'} \n构建码：${r'${env.BUILD_NUMBER}'} \n结果：成功"]

)
}
failure {
dingtalk(
robot: "${r'${dingdingId}'}",
type: 'LINK',
atAll: false,
title: "失败",
messageUrl: "${r'${env.BUILD_URL}'}",
picUrl: 'http://sortserverfat.yto56.com.cn:8080/error.png',
text: ["项目：${r'${env.appName}'}\n分支：${r'${env.BRANCH_NAME}'}\n构建码：${r'${env.BUILD_NUMBER}'}"]
)
}
}
tools {
maven 'maven3.6.3'
}
environment {
//钉钉ID,需要在jenkins上配置
dingdingId = "#{dingdingId}"
//eolinker自动换测试分组ID，需要对应测试人员提供
eolinkerGroupId = #{eolinkerGroupId}
//eolinker自动换测试URL地址，默认不需要修改
eolinkerServerUrl = "#{eolinkerServerUrl}"
//应用jar包存放相对路径
appTargetHome ="#{appTargetHome}"
//应用jar包名称
appName = "#{appName}"
//部署机器的jdk绝对路径，默认不需要修改
remoteJavaHome = "#{remoteJavaHome}"
//部署机器的应用部署绝对路径，默认不需要修改
remoteAppHome = "#{remoteAppHome}"
//需要安全控制的部署环境，安全控制的环境必须在jenkins配置机器访问密码凭证
securityEnv = "#{securityEnv}"
}
//    parameters {
//        booleanParam(name: 'FAST_MODE', defaultValue: false, description: '此操作将会跳过单元测试以及代码质量检查。')
//    }
}


//各个环境机器部署信息
def getSitIps() {
//sit打包分支
env.sitBranch = "#{env.sitBranch}"
//机器的账号密码凭证，非安全控制环境可不配置
env.sitCredentialsId = "#{env.sitCredentialsId}"
//非安全受控环境配置格式：['10.130.36.182|root|6Zn88se6A9Ws','10.130.36.182|root|6Zn88se6A9Ws']
//安全受控环境配置['10.130.36.182']
env.sitSpringProfilesActive="#{env.sitSpringProfilesActive}"
String[] ipList = #{sitIpList}
return ipList
}

def getUatIps() {
//uat打包分支
env.uatBranch = "#{env.uatBranch}"
env.uatCredentialsId = "#{env.uatCredentialsId}";
env.uatSpringProfilesActive="#{env.uatSpringProfilesActive}"
String[] ipList = #{uatIpList}
return ipList
}

def getPreIps() {
//pre生产预发布验证环境分支
env.preBranch = "#{env.preBranch}"
env.preCredentialsId = "#{env.preCredentialsId}";
env.preSpringProfilesActive="#{env.preSpringProfilesActive}"
String[] ipList = #{preIpList}
return ipList
}

def getProIps() {
//pro生产发布分支
env.proBranch = "master"
env.proCredentialsId = "yto-mat-interfacex182-sit";
env.proSpringProfilesActive="prod"
String[] ipList = ['10.130.36.182|root|6Zn88se6A9Ws']
return ipList
}


//初始化安装环境
def installJavaRuntime(deployEnv) {
def ipList = []
if (deployEnv == "sit") {
ipList = getSitIps()
} else if (deployEnv == "uat") {
ipList = getUatIps()
} else if (deployEnv == "fat") {
ipList = getPreIps()
} else if (deployEnv == "pro") {
ipList = getProIps()
}

for (int i = 0; i < ipList.size(); i++) {
def ip = ipList[i]
def userName = ""
def password = ""
if (!"${r'${securityEnv}'}".contains(deployEnv)) {
String[] serverInfos = ip.split('\\|')
ip = serverInfos[0]
userName = serverInfos[1]
password = serverInfos[2]
} else {
userName = userName
password = password
}

try {
def remote = [:]
remote.name = 'installJavaRuntime'
remote.host = ip
remote.user = userName
remote.password = password
remote.allowAnyHosts = true

def jdkHome = "/opt/jdk1.8.0_45/bin/java"
def jenkinsJdkHome = "/var/jenkins_home/jdk1.8.0_45"
def jenkinsApplication = "/var/jenkins_home/application"
def jenkinsData = "/var/jenkins_home/data"
def jdkResult = sshCommand remote: remote, command: "ls ${r'${jdkHome}'}", failOnError: false
if (jdkResult == "" || jdkResult == null) {
echo "Start installing the deployment environment now......"
sshPut remote: remote, from: "${r'${jenkinsJdkHome}'}", into: '/opt/'
sshCommand remote: remote, command: "chmod +x ${r'${jdkHome}'}"
sshPut remote: remote, from: "${r'${jenkinsApplication}'}", into: '/opt/'
sshPut remote: remote, from: "${r'${jenkinsData}'}", into: '/opt/'
} else {
echo "Skip installing deployment environment......."
}
} catch (error) {
echo "${r'${ip}'} The Java runtime environment has been installed error"
}
}
}

//部署
def deploy(deployEnv) {
script {
def ipList = []
if (deployEnv == "sit") {
ipList = getSitIps()
} else if (deployEnv == "uat") {
ipList = getUatIps()
} else if (deployEnv == "pre") {
ipList = getPreIps()
} else if (deployEnv == "pro") {
ipList = getProIps()
}


def path = "${env.WORKSPACE}${appTargetHome}${r'${appName}'}"
for (int i = 0; i < ipList.size(); i++) {
def ip = ipList[i]
def remote = [:]
def userName = ""
def password = ""
if (!"${r'${securityEnv}'}".contains(deployEnv)) {
String[] serverInfos = ip.split('\\|')
ip = serverInfos[0]
userName = serverInfos[1]
password = serverInfos[2]
} else {
userName = userName
password = password
}
remote.name = 'deploy'
remote.host = ip
remote.user = userName
remote.password = password
remote.allowAnyHosts = true
sshPut remote: remote, from: path, into: "${remoteAppHome}"
try {
sshCommand remote: remote, command: "kill -9 \$(ps -aef | grep ${r'${appName}'} | grep -v grep | awk \"{print \$2}\")"
} catch (error) {
echo 'not run pid.. ${error}'
}
sshCommand remote: remote, command: "nohup ${remoteJavaHome} -jar ${remoteAppHome}${r'${appName}'} --spring.profiles.active=${deployEnv} >/dev/null 2>log &"
}
}
}

//执行自动化测试
def eolinkerTestCase() {
def response = ""
try {
response = sh returnStdout: true, script: "curl -i -X POST -H Eo-Secret-Key:HzVXYvq07b604f411a0ae914c4b6217fe8bd5a36020bca8 -H Content-Type:application/x-www-form-urlencoded -d project_id=ppBrS9xb281bd333a94f32f6608af89b9cb6fec56952823 -d space_id=cfJXzLCf4e17df2e0d39343b1c2d4558adebf36690da29a -d case_id=1404 -d report_email=01668423@yto.net.cn -d execute_way=1 -d group_id=${eolinkerGroupId} ${eolinkerServerUrl}"
echo "eolinker success test case response ${response}"
} catch (error) {
echo "eolinker error test case"
}
}

//将打包好的部署文件发送到运维集成要求的机器目录下
def sendProductWareHouse() {
//发送到集成部署机器 /opt/release/20201209
def timeStamp = Calendar.getInstance().getTime().format('YYYYMMdd', TimeZone.getTimeZone('CST'))
def remotePro = [:]
remotePro.name = 'remotePro'
remotePro.host = '10.1.208.77'
remotePro.user = 'logger'
remotePro.password = 'logger@yto'
remotePro.allowAnyHosts = true
sshPut remote: remotePro, from: path, into: "/opt/release/${timeStamp}"
}


