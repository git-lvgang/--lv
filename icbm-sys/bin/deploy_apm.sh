#!/usr/bin/env bash

# REMOTE
SW_REMOTES=(
  #dev
  'http://10.2.111.159/skywalking-agent'
  #sit
  'http://10.2.25.31/skywalking-agent'
  #uat
  'http://10.2.111.128/skywalking-agent'
  #prd 交易
  'http://10.0.39.169/skywalking-agent'
  #prd 办公
  'http://10.2.224.145/skywalking-agent'
)
SW_REMOTE=''

# LOCAL
#apm home
APM_HOME="$HOME/.apm"
#sw latest version link directory
SW_HOME="$APM_HOME/apache-skywalking-apm-bin"

# INNER
#skywalking tar
SW_REMOTE_TAR="apache-skywalking-apm-latest.tar"
#md5 file name
SW_REMOTE_MD5="apache-skywalking-apm-latest.tar.agent.jar.md5"

#skywalking config file
SW_REMOTE_CONFIG="apm.conf"
#skywalking agent jar
SW_LOCAL_AGENT_JAR="$SW_HOME/agent/skywalking-agent.jar"
#skywalking config
SW_LOCAL_CONFIG="apm.conf"

SAFE_MODE=false

function findSwRemote() {
  for url in ${SW_REMOTES[@]}; do
    #echo "Check remote url $url..."
    if curl -sIL -w "%{http_code}\n" -o /dev/null -m 2 $url | grep 200 >/dev/null; then
      echo "use remote url: $url"
      SW_REMOTE=$url
      return 0
#    else
#      echo "remote url $url is invalid, ignored."
    fi
  done

  read -p "None valid remote url, please input a valid remote url:" SW_REMOTE
  echo "Your input remote url is $SW_REMOTE"
}

function deploy() {
  if [ "$SW_REMOTE" == '' ]; then
    findSwRemote
  fi

  mkdir -p "$APM_HOME" && cd "$APM_HOME" || exit

  wget -O /tmp/apache-skywalking-apm.tar.gz ${SW_REMOTE}/${SW_REMOTE_TAR}
  tar -zxf /tmp/apache-skywalking-apm.tar.gz -C "$APM_HOME"
  rm /tmp/apache-skywalking-apm.tar.gz

  LATEST_VERSION="$(ls | grep "apache-skywalking-apm" | grep -v "apache-skywalking-apm-bin" | sort -rV | head -1)"
  ln -sfn "$LATEST_VERSION" "$SW_HOME"

  if curl -sIL -w "%{http_code}\n" -o /dev/null ${SW_REMOTE}/${SW_REMOTE_CONFIG} | grep 404 >/dev/null; then
    echo "remote $SW_REMOTE_CONFIG is not found, ignored."
  else
    wget -O "$APM_HOME/$SW_LOCAL_CONFIG" ${SW_REMOTE}/${SW_REMOTE_CONFIG}
  fi
}

function tryDeploy() {
  #check skywalking agent: /home/$USER/.apm/apache-skywalking-apm-bin/agent/skywalking-agent.jar
  if [ ! -f "$SW_LOCAL_AGENT_JAR" ]; then
    if [[ $SAFE_MODE == true ]]; then
      # is ok
      read -p "skywalking agent $SW_LOCAL_AGENT_JAR is not exist, download from server and deploy! is this ok? [y/n] " isOK
      echo "you entered: $isOK"
      case $isOK in y)
        deploy
        ;;
      esac
    else
      deploy
    fi
  fi
}

function checkMd5() {
  if [ "$SW_REMOTE" == '' ]; then
    findSwRemote
  fi
  S_MD5=$(curl -s ${SW_REMOTE}/${SW_REMOTE_MD5})
  C_MD5=$(md5sum "$SW_LOCAL_AGENT_JAR" | cut -d ' ' -f1)
#   echo "server md5 is $S_MD5, client md5 is $C_MD5"
  if [ "$S_MD5" != "$C_MD5" ]; then
    if [[ $SAFE_MODE == true ]]; then
      # is ok
      read -p "skywalking agent $SW_LOCAL_AGENT_JAR is not latest, download from server and deploy! is this ok? [y/n] " isOK
      echo "you entered: $isOK"
      case $isOK in y)
        deploy
        ;;
      esac
    else
      deploy
    fi
  else
    echo "skywalking agent $SW_LOCAL_AGENT_JAR is latest!"
  fi
}

tryDeploy && checkMd5

echo -e "\033[32mNOTICE: Please make sure the key '$APM_HOME/$SW_LOCAL_CONFIG:SW_AGENT_COLLECTOR_BACKEND_SERVICES' point to the right skywalking oap server.\033[0m"