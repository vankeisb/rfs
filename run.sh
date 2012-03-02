RFS_PORT="$1"
if [ -z "$RFS_PORT" ] 
then
  RFS_PORT="9999"
fi
echo "Starting RFS on port $RFS_PORT"
java -jar jetty-runner-8.1.1.v20120215.jar --port "$RFS_PORT" --path /rfs ./target/rfs 
