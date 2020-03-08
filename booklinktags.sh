#!/bin/bash

if [ $# -lt 1 ]
then
cat << HELP

booklinktags  --  list all booklink docker images posted to dockerhub registry.

EXAMPLE: 
  booklinktags fronted-vue   # list vue.js frontend images
  booklinktags backend       # list backend images
HELP
fi

image="$1"
tags=`wget -q https://registry.hub.docker.com/v1/repositories/mrazjava/booklink-${image}-master/tags -O -  | sed -e 's/[][]//g' -e 's/"//g' -e 's/ //g' | tr '}' '\n'  | awk -F: '{print $3}'`

if [ -n "$2" ]
then
#    tags=` echo "${tags}" | grep "$2" `
    tags=` echo "${tags}"`
fi

echo "${tags}"
