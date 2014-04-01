#!/bin/sh

######################################################################################################
# ECLIPSE PROJECT GENERATION SCRIPT
######################################################################################################

# UNCOMMENT TO DEBUG
# set -x

######################################################################################################
# CHECK IF THE PLAY COMMAND EXISTS
######################################################################################################

if hash play 2>/dev/null;
then
	PLAY=play
else 
	echo "Play command not found, expecting it in /opt/devtools/play/play-2.2.2/play"
	PLAY=/opt/devtools/play/play-2.2.2/play 
fi

######################################################################################################
# Generation loop in target dir
######################################################################################################

generate () {
for dir in $1/*/
do
	cd $dir
	echo "entered $dir"
	
	$PLAY "eclipse with-source=true"
	cd ..
done
}

generate .
