#!/bin/bash

if [ $# -eq 0 ] || [ $1 == 'help' ]
then
cat << HELP
sandbox  --  run Booklink locally simulating desired environment

Pulls docker images from dockerhub for the desired environment. Once pulled,
they are started. For local environment there is nothing to pull as sandbox
expects the images have been built directly from the sources (see frontend/backend
project Dockerfile).

To get a list of deployed live tags which are needed as additional arguments to
the [live] parameter, run booklinktags.sh script.

EXAMPLE:
  live 0.1.4 0.2.8   : pulls tagged master images and runs them
  pre                : pulls latest master images and runs them
  stg                : pulls latest develop images and runs them
  local              : runs latest local images built from sources
  help               : this message
HELP
exit
fi

if [ $1 == 'live' ] || [ $1 == 'pre' ] || [ $1 == 'stg' ] || [ $1 == 'local' ]
then
 SOURCE=$1
 if [ $1 == 'live' ] && [ $# -lt 3 ]
 then
   echo "[live] parameter must be followed by [frontend version] and [backend version] - see help"
   exit
 fi
else
 echo "INVALID PARAMETER. Allowed values are [live <FE_IMG> <BE_IMG>|pre|stg|local|help]. DEFAULT: pre"
 exit
fi

if [ $SOURCE == 'live' ]
then
 echo "$2 .... $3"
 export FE_IMG_TAG=$2
 export BE_IMG_TAG=$3
 echo "--------------------------------------------------"
 echo "+ validating *live* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml pull frontend backend
 echo "--------------------------------------------------"
 echo "+ simulating LIVE environment (fronend=$FE_IMG_TAG, backend=$BE_IMG_TAG)"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml up
fi

if [ $SOURCE == 'pre' ]
then
 echo "--------------------------------------------------"
 echo "+ validating *pre-release* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/pre.yml pull frontend backend
 echo "--------------------------------------------------"
 echo "+ simulating PRE-RELEASE environment"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/pre.yml up
fi

if [ $SOURCE == 'stg' ]
then
 echo "--------------------------------------------------"
 echo "+ validating *staging* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/stg.yml pull frontend backend
 echo "--------------------------------------------------"
 echo "+ starting STAGING environment"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/stg.yml up
fi

if [ $SOURCE == 'local' ]
then
 echo "--------------------------------------------------"
 echo "+ starting LOCAL environment"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/local.yml up
fi
