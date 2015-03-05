#!/bin/sh

######################################################################################################
# ECLIPSE PROJECT GENERATION SCRIPT
######################################################################################################

# UNCOMMENT TO DEBUG
# set -x

######################################################################################################
# CHECK IF THE PLAY COMMAND EXISTS
######################################################################################################

if hash activator 2>/dev/null;
then
	ACTIVATOR=activator
else
	echo "Activator command not found, expecting it in /opt/activator/activator"
	ACTIVATOR=/opt/activator/activator
fi

######################################################################################################
# Generation loop in target dir
######################################################################################################

generate () {
for dir in $1/*/
do
	cd ${dir}
	echo "entered $dir"
	rm -rf app-2.11 logs project/project project/target projectFilesBackup target .*
#	${ACTIVATOR} "clean"
#	${ACTIVATOR} "idea with-sources=yes"
#	${ACTIVATOR} "eclipse with-source=true"
#	${ACTIVATOR} "compile"
#	${ACTIVATOR} "test"
	cd ..
done
}

generate .
