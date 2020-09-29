#!/bin/bash

mongorestore --nsInclude=openlibrary.* --verbose --gzip --archive=/tmp/mongodump/openlibrary-mongo.archive