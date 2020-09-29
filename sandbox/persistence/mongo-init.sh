#!/bin/bash

# openlibrary-mongo.archive is a binary dump of a database created with booklink-openlibrary import process
# mongo binary archive is not provided (excluded with .gitignore) but the database itself is available via sandbox
# docker image
#
mongorestore --nsInclude=openlibrary.* --verbose --gzip --archive=/tmp/mongodump/openlibrary-mongo.archive