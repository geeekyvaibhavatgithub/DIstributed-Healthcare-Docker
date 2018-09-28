#!/bin/sh -e

tail -F "${NIFI_HOME}/logs/nifi-app.log" &
"${NIFI_HOME}/bin/nifi.sh" run &
nifi_pid="$!"


trap "echo Received trapped signal, beginning shutdown...;" KILL TERM HUP INT EXIT;

wait ${nifi_pid}
