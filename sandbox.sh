#!/bin/bash

if [ $# -eq 0 ] || [ "$1" == "help" ]
then
cat << HELP

sandbox  --  simulate Booklink environment on a localhost

Pulls most recent docker images from dockerhub for the desired environment, and starts
them (except for [cust] mode). To get a list of deployed live tags, run booklinktags.sh
script.

SYNTAX:
  MODE [-f] [-b]

-f --frontend : image tag for frontend-vue, required for [live], optional for [cust]
-b --backend  : image tag for the backend, required for [live], optional for [cust]

EXAMPLE:
  live -f v0.1.4 -b v0.2.8    : run tagged live (or archived) images (eg: v0.1.4 frontend-vue, v0.2.8 backend)
  pre                         : run pre-release candidate images (master branch)
  stg                         : run staging snapshot images (develop branch)
  cust [-f local] [-b local]  : run custom built images; defaults to :local tag (custom branch)
  help                        : show this message

HELP
exit
fi

RUNENV=$1

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -f|--frontend)
    export FE_IMG_TAG="$2"
    shift # past argument
    shift # past value
    ;;
    -b|--backend)
    export BE_IMG_TAG="$2"
    shift # past argument
    shift # past value
    ;;
    *)    # non-flagged option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

echo "[$RUNENV]"
if [ "$1" = "live" ] || [ "$1" = "pre" ] || [ "$1" = "stg" ] || [ "$1" = "cust" ]
then
 SOURCE=$1
 if [[ "$RUNENV" = "live" && (-z "$FE_IMG_TAG" || -z "$BE_IMG_TAG") ]];
 then
   echo "[live] parameter requires frontend and backend tags - see help"
   exit
 fi
 if [ "$RUNENV" = "cust" ]
 then
   if [ -z "$FE_IMG_TAG" ]
   then
     export FE_IMG_TAG=local
   fi
   if [ -z "$BE_IMG_TAG" ]
   then
     export BE_IMG_TAG=local
   fi
 fi
else
 echo "INVALID INPUT. Expecting: live -f -b | pre | stg | cust [-f][-b] | help"
 exit
fi

if [ $RUNENV = "live" ]
then
 echo "--------------------------------------------------"
 echo "+ validating *live* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml pull frontend backend
 echo "--------------------------------------------------"
 echo "+ simulating LIVE environment (frontend=$FE_IMG_TAG, backend=$BE_IMG_TAG)"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml up
fi

if [ $RUNENV = "pre" ]
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

if [ $RUNENV = "stg" ]
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

if [ $RUNENV = "cust" ]
then
 echo "--------------------------------------------------"
 echo "+ starting DEV environment (frotnend=$FE_IMG_TAG, backend=$BE_IMG_TAG)"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/cust.yml up
fi