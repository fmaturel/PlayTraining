#!/bin/sh

######################################################################################################
# PLAY GENERIC APP PACKAGING SCRIPT
######################################################################################################

# UNCOMMENT TO DEBUG
# set -x

APP=videostore

# Clean current distribution & tmp directories
######################################################################################################
clean () {
	for dir in $*
	do
		rm -rf $dir
		check "clean failed!"
		echo ">>> Cleaned $dir"
	done
}

check() {
	if [ $? != 0 ]
	then
		echo "[!] $1"
		exit 1;
	fi
}

# Run the play dist command and add needed files to the distribution
######################################################################################################
dist () {

	# Check if the play command exists
	if hash play 2>/dev/null;
	then
		PLAY=play
	else 
		echo "Play command not found, expecting it in /opt/devtools/play/play-2.2.2/play"
		PLAY=/opt/devtools/play/play-2.2.2/play 
	fi
	
	check "play dist failed!"

	# Do the play dist command
	$PLAY dist

	# Unzip current distribution customize distribution
	echo ">>> Unzip current distribution customize distribution"
	unzip -q target/universal/$APP-*.zip -d tmp

	ZIPNAME=`ls tmp`
    TARGET_DIR=tmp/$ZIPNAME
    
	# Delete the public directory
	echo ">>> Delete /public and /bin from distribution"
	rm -rf $TARGET_DIR/public
	rm -rf $TARGET_DIR/bin

	# Add public resources as a separate directory
	echo ">>> Add public resources as a separate directory"
	cp -r public $TARGET_DIR

	# Add start script and conf @ root
	cp conf/prod/* $TARGET_DIR

	# Remove svn files
	echo ">>> Remove svn files if needed"
	find $TARGET_DIR -name ".svn" -exec rm -rf {} \;

	# Zip everything again
	echo ">>> Zip everything again"
	mkdir dist
	cd $TARGET_DIR
	zip -r -q ../../dist/$ZIPNAME.zip .
	cd ../..
}

echo "
#########################################
1. Cleaning previous release...
#########################################"
# cleaning dist & tmp directories
clean dist tmp

echo "
#########################################
2. Packaging PLAY GENERIC APP...     
#########################################"

dist

echo "
#########################################
3. PACKAGING DONE     
#########################################"
clean tmp
echo ">>> Package is ready in dist/$ZIPNAME.zip"

exit 0
