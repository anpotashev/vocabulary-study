#!/bin/sh

current_release=R2.0.0

if [ $# -ne 3 ]; then
	echo "Usage: ./create_sql.sh <ddl|dml|postddl> <name> <description>"
	echo "example:"
	echo "./create_sql.sh ddl create_new_table_my \"Создать таблицу my\""
	exit 1
fi
rollback_suffix=_rollback
author=`git config user.name`
dir=`pwd`/src/main/resources/db/changelog/$current_release/$1
mkdir -p $dir
date=`date +%G%m%d%H%M%S`
filename_without_extension="$date"_$2
filename=$filename_without_extension.sql
rollback_filename="$date"_$2$rollback_suffix.sql
filename_path=$dir/$filename
rollback_filename_path=$dir/$rollback_filename

echo "-- Author: $author" >> $filename_path
echo "-- Description: $3" >> $filename_path
echo "/*" >> $filename_path
echo "  - changeSet:" >> $filename_path
echo "      id: $filename_without_extension" >> $filename_path
echo "      author: $author" >> $filename_path
echo "      runOnChange: false" >> $filename_path
echo "      comment: $3" >> $filename_path
echo "      changes:" >> $filename_path
echo "        - sqlFile:" >> $filename_path
echo "            path: $filename" >> $filename_path
echo "            relativeToChangelogFile: true" >> $filename_path
echo "      rollback:" >> $filename_path
echo "        - sqlFile:" >> $filename_path
echo "            path: $rollback_filename" >> $filename_path
echo "            relativeToChangelogFile: true" >> $filename_path
echo "*/" >> $filename_path

echo "-- Rollback --" >> $rollback_filename_path
echo "-- Author: $author" >> $rollback_filename_path
echo "-- Description: $3" >> $rollback_filename_path

git add $filename_path 
git add $rollback_filename_path 
