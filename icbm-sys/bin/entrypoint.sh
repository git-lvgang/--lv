#!/bin/sh
printf "********** STARTING SPRINGBOOT APP **********\n"

if [ "$DEBUG" = true ]; then
  printf "Running the application in debug mode.\n"
  REMOTE_PORT=${REMOTE_PORT:-8000}
  JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=$REMOTE_PORT,suspend=n"
fi

# Improve startup time by using less complex random seed
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
SW_OPTS=""

if [ "$SKYWALKING_ENABLED" = true ]; then
  printf "Enable Skywalking.\n"
  SW_HOME=${SW_HOME:-"$HOME/.apm"}
  printf "Skywalking Home: $SW_HOME.\n"
  SW_JAR_FILE="$SW_HOME/apache-skywalking-apm-bin/agent/skywalking-agent.jar"
  if [ ! -f "$SW_JAR_FILE" ];then
    printf "Can not enable skywalking as agent not found: $SW_JAR_FILE, please add skywalking sidecar first.\n"
  else
    if [ -z "$SW_SERVICES"]; then
      key="SW_AGENT_COLLECTOR_BACKEND_SERVICES"
      SW_SERVICES=`cat "$SW_HOME/apm.conf" | grep -m 1 "^\s*$key" | awk -F'=' '{ print $2 }' | sed s/[[:space:]]//g`
    fi
    printf "Skywalking services: $SW_SERVICES\n"
    SW_OPTS="-javaagent:$SW_JAR_FILE -Dskywalking.agent.service_name=$APP_NAME -Dskywalking.collector.backend_service=$SW_SERVICES"
  fi
fi

JAVA_COMMAND="java $JAVA_OPTS $SW_OPTS -Dlogging.config=$CONTAINER_HOME/config/$PROFILES/logback-spring.xml -Dspring.config.location=$CONTAINER_HOME/config/$PROFILES/ -cp $CONTAINER_HOME/resources:$CONTAINER_HOME/classes:$CONTAINER_HOME/libs/* $START_CLASS $SPRINGBOOT_OPTS"

printf "JAVA_OPTS: $JAVA_OPTS\n"
printf "PROFILES: $PROFILES\n"
printf "SPRINGBOOT_OPTS: $SPRINGBOOT_OPTS\n"
printf "JAVA_COMMAND: $JAVA_COMMAND\n"
printf "*********************************************\n"

exec $JAVA_COMMAND
