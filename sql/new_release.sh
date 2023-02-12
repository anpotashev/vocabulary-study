#!/usr/bin/env sh
if [ $# -ne 2 ]; then
	echo "Usage: ./new_release.sh <current_version> <new_version>"
	echo "example:"
	echo "./new_release.sh 4.1.0 4.2.0"
	exit 1
fi

currentversion=$1
nextversion=$2
sed -i "" -e "1,/\<version\>$currentversion\<\/version\>/s/\<version\>$currentversion\(.*\)\<\/version\>/\<version\>$nextversion\1\<\/version\>/" pom.xml
sed -i "" -e "1,/\<version\>$currentversion\<\/version\>/s/\<version\>$currentversion\(.*\)\<\/version\>/\<version\>$nextversion\1\<\/version\>/" pom.xml

echo "
  - include:
      file: R$nextversion/changelog.yaml
      relativeToChangelogFile: true" >> src/main/resources/db/changelog/db.changelog-master.yaml
mkdir src/main/resources/db/changelog/R$nextversion
mkdir src/main/resources/db/changelog/R$nextversion/ddl
mkdir src/main/resources/db/changelog/R$nextversion/dml
mkdir src/main/resources/db/changelog/R$nextversion/fun

$schema: https://raw.githubusercontent.com/sunildabburi/liquibase-schema/main/liquibase-schema-3.10.json

echo "\$schema: https://raw.githubusercontent.com/sunildabburi/liquibase-schema/main/liquibase-schema-3.10.json

databaseChangeLog:
  - changeSet:
      id: R$nextversion
      author: system
      changes:
        - tagDatabase:
            tag: R$nextversion
  - include:
      file: ddl/changelog.yaml
      relativeToChangelogFile: true
  - include:
      file: dml/changelog.yaml
      relativeToChangelogFile: true
  - include:
      file: fun/changelog.yaml
      relativeToChangelogFile: true
" > src/main/resources/db/changelog/R$nextversion/changelog.yaml

echo "\$schema: https://raw.githubusercontent.com/sunildabburi/liquibase-schema/main/liquibase-schema-3.10.json

databaseChangeLog: []" > src/main/resources/db/changelog/R$nextversion/ddl/changelog.yaml

echo "\$schema: https://raw.githubusercontent.com/sunildabburi/liquibase-schema/main/liquibase-schema-3.10.json

databaseChangeLog: []" > src/main/resources/db/changelog/R$nextversion/dml/changelog.yaml

echo "\$schema: https://raw.githubusercontent.com/sunildabburi/liquibase-schema/main/liquibase-schema-3.10.json

databaseChangeLog: []" > src/main/resources/db/changelog/R$nextversion/fun/changelog.yaml

git add src/main/resources/db/changelog/R$nextversion/changelog.yaml
git add src/main/resources/db/changelog/R$nextversion/ddl/changelog.yaml
git add src/main/resources/db/changelog/R$nextversion/dml/changelog.yaml
git add src/main/resources/db/changelog/R$nextversion/fun/changelog.yaml
