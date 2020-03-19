#!/bin/bash

if [ $# -eq 0 ] || [ "$1" == "help" ]
then
cat << HELP

sandbox  --  simulate Booklink environment on a localhost

Pulls most recent docker images from dockerhub for the desired environment,
and starts them. To get a list of deployed live tags, run booklinktags.sh
script.

FLAGS:

-f --frontend : image tag for frontend-vue
  Required for [live], optional for [local]. If provided for [local] without
  the backend, then backend is ran regardless with a :local tag.

-b --backend  : image tag for the backend
  Required for [live], optional for [local]. If provided for [local] without
  the frontend, then frontend is ran regardless with a :local tag.

EXAMPLE:
  live -f v0.1.4 -b v0.2.8    : run tagged live (or archived) images (example:
                                v0.1.4 frontend-vue, v0.2.8 backend)
  pre                         : run pre-release candidate images (:master)
  stg                         : run staging snapshot images (:develop)
  local [-f] [-b]             : run custom built images if at least one tag is
                                provided, otherwise launch DB only
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

if [ "$1" = "live" ] || [ "$1" = "pre" ] || [ "$1" = "stg" ] || [ "$1" = "local" ]
then
 SOURCE=$1
 if [[ "$RUNENV" = "live" && (-z "$FE_IMG_TAG" || -z "$BE_IMG_TAG") ]];
 then
   echo "[live] parameter requires frontend and backend tags - see help"
   exit
 fi
 if [ "$RUNENV" = "local" ]
 then
   if [[ -z "$FE_IMG_TAG" && (! -z "$BE_IMG_TAG") ]];
   then
     export FE_IMG_TAG=local
   fi
   if [[ -z "$BE_IMG_TAG" && (! -z "$FE_IMG_TAG") ]];
   then
     export BE_IMG_TAG=local
   fi
 fi
else
 echo "INVALID INPUT. Expecting: live -f -b | pre | stg | local [-f][-b] | help"
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

if [ $RUNENV = "local" ]
then
 echo "--------------------------------------------------"
 echo "+ starting LOCAL dev environment"
 echo "--------------------------------------------------"
 if [[ (! -z "$BE_IMG_TAG") && (! -z "$FE_IMG_TAG") ]];
 then
   echo "* w/ frotnend=$FE_IMG_TAG, backend=$BE_IMG_TAG"
   echo "--------------------------------------------------"
   docker-compose -f docker-compose/local.yml up pg admin frontend backend
 else
   echo "* persistence only"
   echo "--------------------------------------------------"
   export FE_IMG_TAG=
   export BE_IMG_TAG=
   docker-compose -f docker-compose/local.yml up pg admin
 fi
fi