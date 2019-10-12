#!/bin/bash

/opt/bin/generate_config > /opt/airbot/config.json

if [ ! -e /opt/airbot/config.json ]; then
  echo No airbot configuration file, the base_graph image is not intended to be run directly. 1>&2
  exit 1
fi

if [ ! -z "$SE_OPTS" ]; then
  echo "appending airbot options: ${SE_OPTS}"
fi

rm -f /tmp/.X*lock

#java ${JAVA_OPTS} -cp ${JAVA_CLASSPATH:-"/opt/airbot/*:."} org.openqa.grid.selenium.GridLauncherV3 \
#  -role node \
#  -hub http://$HUB_PORT_4444_TCP_ADDR:$HUB_PORT_4444_TCP_PORT/grid/register \
#  ${REMOTE_HOST_PARAM} \
#  -nodeConfig /opt/selenium/config.json \
#  ${SE_OPTS}