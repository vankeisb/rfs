#!/bin/sh

RFS_PORT="$1"
if [ -z "$RFS_PORT" ] 
then
  RFS_PORT="9999"
fi
echo "Starting RFS on port $RFS_PORT"
java -jar jetty-runner-7.6.1.v20120215.jar --port "$RFS_PORT" --path /rfs ./target/rfs
