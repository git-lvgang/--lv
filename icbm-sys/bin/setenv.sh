#!/usr/bin/env sh

BASE_DIR=`cd "$(dirname $0)/.." >/dev/null; pwd`

#############  Please Modify the following custom variables ############

# application name
APP_NAME=springboot-demo

# jvm options
JVM_OPTS="-server -Xms2g -Xmx2g\
 -XX:+HeapDumpOnOutOfMemoryError\
 -XX:HeapDumpPath=${BASE_DIR}/logs/dump.hprof"

#########################################################################
