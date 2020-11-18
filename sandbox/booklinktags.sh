#!/bin/bash

if [ $# -lt 1 ]
then
cat << HELP

booklinktags  --  list all booklink docker images posted to dockerhub registry.

EXAMPLE: 
  booklinktags frontend-vue      # list vue.js frontend images
  booklinktags backend           # list backend images
  booklinktags openlibrary-depot # list openlibrary.org integration images
  booklinktags mongo             # list mongo (book sources) images
HELP
fi

image="$1"
tags=`wget -q https://registry.hub.docker.com/v1/repositories/mrazjava/booklink-${image}/tags -O -  | sed -e 's/[][]//g' -e 's/"//g' -e 's/ //g' | tr '}' '\n'  | awk -F: '{print $3}' | grep -E '[0-9]' | sort -nr`

if [ -n "$2" ]
then
    tags=` echo "${tags}"`
fi

echo "${tags}"
