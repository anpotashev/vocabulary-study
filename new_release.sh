#!/usr/bin/env sh
if [ $# -ne 2 ]; then
	echo "Usage: ./new_release.sh <current_version> <new_version>"
	echo "example:"
	echo "./new_release.sh 1.0.0 1.1.0"
	exit 1
fi

currentversion=$1
nextversion=$2
git checkout -b release/$nextversion
sed -i "" -e "1,/\<version\>$currentversion\<\/version\>/s/\<version\>$currentversion\(.*\)\<\/version\>/\<version\>$nextversion\1\<\/version\>/" pom.xml
modules=("bh" "sql")
for i in "${modules[@]}"
do
  cd $i
  ./new_release.sh $currentversion $nextversion
  cd ..
done
git commit -am "new version $nextversion"
git push  --set-upstream origin release/$nextversion