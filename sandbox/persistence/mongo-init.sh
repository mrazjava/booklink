#!/bin/bash

#mongorestore --username root --password pass123 --authenticationDatabase admin --nsInclude=openlibrary.* --verbose --gzip --archive=/tmp/mongodump/openlibrary-mongo.archive
mongorestore --nsInclude=openlibrary.* --verbose --gzip --archive=/tmp/mongodump/openlibrary-mongo.archive