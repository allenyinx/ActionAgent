#!/bin/bash
#
# IMPORTANT: Change this file only in directory StandaloneDebug!

source /opt/bin/functions.sh

export GEOMETRY="$SCREEN_WIDTH""x""$SCREEN_HEIGHT""x""$SCREEN_DEPTH"

function shutdown {
  kill -s SIGTERM $NODE_PID
  wait $NODE_PID
}

if [ ! -z "$SE_OPTS" ]; then
  echo "appending selenium options: ${SE_OPTS}"
fi

rm -f /tmp/.X*lock

SERVERNUM=$(get_server_num)

DISPLAY=$DISPLAY \
  xvfb-run -n $SERVERNUM --server-args="-screen 0 $GEOMETRY -ac +extension RANDR" \
  java ${JAVA_OPTS} -jar /home/airbot/airgent.jar \
  ${SE_OPTS} &
NODE_PID=$!

trap shutdown SIGTERM SIGINT
for i in $(seq 1 10)
do
  xdpyinfo -display $DISPLAY >/dev/null 2>&1
  if [ $? -eq 0 ]; then
    break
  fi
  echo Waiting xvfb...
  sleep 0.5
done

fluxbox -display $DISPLAY &

x11vnc -forever -usepw -shared -rfbport 5900 -display $DISPLAY &

#export SAF_REST_API_OPTS='-Xms1024m -Xmx2048m'
#/home/seluser/auto/saf-rest-api/bin/saf-rest-api &

#/home/seluser/auto/filebeat-6.2.1-linux-x86_64/filebeat -e -c /home/seluser/auto/filebeat-6.2.1-linux-x86_64/filebeat.yml -d "publish" &

/home/airbot/auto/noVNC/utils/launch.sh --vnc localhost:5900 &

wait $NODE_PID