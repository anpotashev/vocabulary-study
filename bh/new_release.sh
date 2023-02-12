#!/usr/bin/env sh
if [ $# -ne 2 ]; then
	echo "Usage: ./new_release.sh <current_version> <new_version>"
	echo "example:"
	echo "./new_release.sh 1.0.0 1.1.0"
	exit 1
fi

currentversion=$1
nextversion=$2
sed -i "" -e "1,/\<version\>$currentversion\<\/version\>/s/\<version\>$currentversion\(.*\)\<\/version\>/\<version\>$nextversion\1\<\/version\>/" pom.xml
sed -i "" -e "1,/\<version\>$currentversion\<\/version\>/s/\<version\>$currentversion\(.*\)\<\/version\>/\<version\>$nextversion\1\<\/version\>/" pom.xml
