# APP
资管业务管理系统基础应用


## 一、命令行参数方式运行

mvn package -Djib.to.auth.username=${username} -Djib.to.auth.password=${password} 

mvn package -DdockerVersion=1.0.0-SNAPSHOT-1 -DafRepoName=ambs-docker-dev -Daf_username=${username}  -Daf_password=${password}
执行该命令即可生成程序JAR包和容器镜像TAR包,并上传至制品仓库。

username/password为你的artifactory制品仓库账号和密码。
