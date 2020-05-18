#!/bin/bash

set -e
set -u

function create_database() {
	local database=$1
	echo "- CREATE DATABASE $database;"
	echo "- GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
	    CREATE DATABASE $database;
	    GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
  BOOKLINK_DB_COUNT=0
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_database $db
		let "BOOKLINK_DB_COUNT=BOOKLINK_DB_COUNT+1"
	done
	echo "created $BOOKLINK_DB_COUNT database(s)"
fi