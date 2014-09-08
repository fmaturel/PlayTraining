#! /bin/sh

### BEGIN INIT INFO
# Provides:          videostore
# Required-Start:    $all
# Required-Stop:     $all
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description:
# Description:
### END INIT INFO

# VIDEOSTORE APPLICATION VARIABLES
##################################

APP_NAME="videostore"
APP_PATH="$( cd "$( dirname "$0" )" && pwd )"
APP_PORT=9000
APP_HTTPS_PORT=9443
APP_CONFIG=$APP_PATH/$APP_NAME-prod.conf
APP_LOGGER=$APP_PATH/$APP_NAME-logger.xml
APP_EXTRA="-XX:PermSize=128m -XX:MaxPermSize=512m -Xms128m -Xmx1024m -server"

# APP ACTIONS
##################################

# Export for configuration files using APP_PATH
export APP_PATH=$APP_PATH
export APP_NAME=$APP_NAME

start() {
	echo "Running $APP in directory [$APP_PATH]"
	# Use -Dhttp.port=disabled to disable http
	
    java $* $APP_EXTRA -cp "$APP_PATH/public:$APP_PATH/lib/*" \
            -Dcom.sun.management.jmxremote.port=1099 \
            -Dcom.sun.management.jmxremote.ssl=false \
            -Dcom.sun.management.jmxremote.authenticate=false \
            -Djava.net.preferIPv4Stack=true \
            -Dhttp.port=$APP_PORT \
            -Dconfig.file=$APP_CONFIG \
            -Dlogger.file=$APP_LOGGER \
            -Dhttps.port=$APP_HTTPS_PORT \
            -Dhttps.keyStore=$APP_PATH/conf/.keystore \
            -Dhttps.keyStorePassword=laformationplay \
            play.core.server.NettyServer $APP_PATH &
}

stop() {
	if [ -f $APP_PATH/RUNNING_PID ]; then
		kill -15 `cat $APP_PATH/RUNNING_PID`
		exit 0
	else
		echo "No $APP_NAME application is running"
	fi
}

case "$1" in
  start)
    echo "Starting $APP_NAME..."
    start
    echo "$APP_NAME started."
    ;;
  stop)
    echo "Stopping $APP_NAME..."
    stop
    echo "$APP_NAME stopped."
    ;;
  restart)
    echo  "Restarting $APP_NAME..."
    stop
    sleep 2
    start
    echo "$APP_NAME restarted."
    ;;
  *)
    echo "Usage: $APP_NAME {start|stop|restart}" >&2
    exit 1
    ;;
esac

exit 0
