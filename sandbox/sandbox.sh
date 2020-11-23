#!/bin/bash

if [ $# -eq 0 ] || [ "$1" == "help" ]
then
cat << HELP

sandbox  --  simulate Booklink environment on a localhost

Pulls most recent docker images from dockerhub for the desired environment,
and starts them. To get a list of deployed live tags, run booklinktags.sh
script. Depot environment is included if either -f or -b option is enabled
(or both).

FLAGS:

-f --frontend : image tag for frontend-vue
  Required for live, ignored for pre and stg, optional for local.
  If provided to local without the -b, then -b is forced with :develop tag.

-b --backend  : image tag for the backend
  Required for live, ignored for pre and stg, optional for local.
  If provided to local without the -f, then frontend is skipped.

-d --depot    : image tag for book source integration
  Optional. If not provided, "latest" is used.

--portP       : port on which to run PostgreSQL; defaults to 5432
--portM       : port on which to run MongoDB; defaults to 27017
--portF       : port on which to run the frontend: defaults to 8090
--portB       : port on which to run the backend; defaults to 8080
--portD       : port on which to run the depot; defaults to 8070

EXAMPLE:
  live                          : run latest live releases
  live -f v0.1.4 -b v0.2.8      : run tagged live (or archived) images (example:
                                  v0.1.4 frontend-vue, v0.2.8 backend) and latest depot
  pre                           : run pre-release candidate images (:master)
  stg                           : run staging snapshot images (:develop)
  local                         : enable all persistence environments, no backend, no frontend, no depot
  local -b                      : run custom built backend (:local), depot (:latest), skip frontend
  local -b foo -d bar -f        : run custom built backend (:foo), custom depot (:bar), frontend (:local)
  local -f                      : run custom built frontend (:local), staged backend (:develop)
  help                          : show this message

HELP
exit
fi

RUNENV=$1

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -f|--frontend)
    if [[ -z "$2" || ("$2" =~ ^-.*) ]];
    then
      export FE_IMG_TAG=local
    else
      export FE_IMG_TAG="$2"
    fi
    shift # past argument
    ;;
    -b|--backend)
    if [[ -z "$2" || ("$2" =~ ^-.*) ]];
    then
      export BE_IMG_TAG=local
    else
      export BE_IMG_TAG="$2"
    fi
    shift # past argument
    ;;
    -d|--depot)
    if [[ -z "$2" || ("$2" =~ ^-.*) ]];
    then
      export OL_IMG_TAG=latest
    else
      export OL_IMG_TAG="$2"
    fi
    shift # past argument
    ;;
    --portF)
    if [[ ! -z "$2" ]] && [[ ! "$2" =~ ^-.* ]];
    then
      export BL_F_PORT="$2"
    fi
    shift # past argument
    ;;
    --portB)
    if [[ ! -z "$2" ]] && [[ ! "$2" =~ ^-.* ]];
    then
      export BL_B_PORT="$2"
    fi
    shift # past argument
    ;;
    --portP)
    if [[ ! -z "$2" ]] && [[ ! "$2" =~ ^-.* ]];
    then
      export BL_P_PORT="$2"
    fi
    shift # past argument
    ;;
    --portM)
    if [[ ! -z "$2" ]] && [[ ! "$2" =~ ^-.* ]];
    then
      export BL_M_PORT="$2"
    fi
    shift # past argument
    ;;
    *)    # non-flagged option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

if [[ -z "$OL_IMG_TAG" ]];
then
  export OL_IMG_TAG=latest
  export OL_MONGO_IMG_TAG=latest
else
  export OL_MONGO_IMG_TAG=${OL_IMG_TAG}-4.4.0
fi

if [[ -z "$BL_F_PORT" ]]
then
  export BL_F_PORT=8090
fi
if [[ -z "$BL_B_PORT" ]]
then
  export BL_B_PORT=8080
fi
if [[ -z "$BL_D_PORT" ]]
then
  export BL_D_PORT=8070
fi
if [[ -z "$BL_P_PORT" ]]
then
  export BL_P_PORT=5432
fi
if [[ -z "$BL_M_PORT" ]]
then
  export BL_M_PORT=27017
fi

if [ "$1" = "live" ] || [ "$1" = "pre" ] || [ "$1" = "stg" ] || [ "$1" = "local" ]
then
 SOURCE=$1
 if [[ "$RUNENV" = "live" && (-z "$FE_IMG_TAG" || -z "$BE_IMG_TAG") ]];
 then
   if [[ -z "$FE_IMG_TAG" ]]
   then
     export FE_IMG_TAG=latest
   fi
   if [[ -z "$BE_IMG_TAG" ]]
   then
     export BE_IMG_TAG=latest
   fi
 fi
 if [ "$RUNENV" = "local" ]
 then
   if [[ -z "$FE_IMG_TAG" && (! -z "$BE_IMG_TAG") ]];
   then
     export FE_IMG_TAG=
   fi
   if [[ -z "$BE_IMG_TAG" && (! -z "$FE_IMG_TAG") ]];
   then
     # FE developer that builds a local image will probably not be interested in running off a
     # local backend image (which implies building one) - so a sensible default here is staging
     # backend image
     export BE_IMG_TAG=develop
   fi
   if [[ -z "$FE_IMG_TAG" && -z "$BE_IMG_TAG" ]];
   then
     export BL_NODEPOT=Y
   fi
 fi
else
 echo "INVALID INPUT. Expecting: live [-f][-b][-d] | pre [-d] | stg [-d] | local [-f][-b][-d] | help"
 exit
fi

echo "Booklink PORT usage on host system:"

if [[ ! "$RUNENV" = "local" ]] || [[ ! -z "$FE_IMG_TAG" ]];
then
  echo "* Frontend: $BL_F_PORT"
fi
if [[ ! "$RUNENV" = "local" ]] || [[ ! -z "$BE_IMG_TAG" ]];
then
  echo "* Backend: $BL_B_PORT"
fi
if [[ ! "$RUNENV" = "local" ]] || [[ -z "$BL_NODEPOT" ]];
then
  echo "* Depot: $BL_D_PORT"
fi
echo "* PostgreSQL: $BL_P_PORT"
echo "* MongoDB: $BL_M_PORT"

if [ $RUNENV = "live" ]
then
 export DB_SCHEMA="$RUNENV-$BE_IMG_TAG"
 echo "--------------------------------------------------"
 echo "+ validating *live* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml -f docker-compose/persistence.yml pull frontend backend depot
 echo "--------------------------------------------------"
 echo "+ simulating LIVE environment (frontend=$FE_IMG_TAG, backend=$BE_IMG_TAG, depot=$OL_IMG_TAG)"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/live.yml -f docker-compose/persistence.yml up --remove-orphans
fi

if [ $RUNENV = "pre" ]
then
 export DB_SCHEMA=$RUNENV
 echo "--------------------------------------------------"
 echo "+ validating *pre-release* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/pre.yml -f docker-compose/persistence.yml pull frontend backend depot
 echo "--------------------------------------------------"
 echo "+ simulating PRE-RELEASE environment"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/pre.yml -f docker-compose/persistence.yml up --remove-orphans
fi

if [ $RUNENV = "stg" ]
then
 export DB_SCHEMA=$RUNENV
 export OL_IMG_TAG=develop
 echo "--------------------------------------------------"
 echo "+ validating *staging* docker images"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/stg.yml -f docker-compose/persistence.yml pull frontend backend depot
 echo "--------------------------------------------------"
 echo "+ starting STAGING environment"
 echo "--------------------------------------------------"
 docker-compose -f docker-compose/stg.yml -f docker-compose/persistence.yml up --remove-orphans
fi

if [ $RUNENV = "local" ]
then
 export DB_SCHEMA=$RUNENV
 echo "--------------------------------------------------"
 echo "+ starting LOCAL dev environment"
 echo "--------------------------------------------------"
 if [[ (! -z "$BE_IMG_TAG") && (! -z "$FE_IMG_TAG") ]];
 then
   echo "* w/ frontend=$FE_IMG_TAG, backend=$BE_IMG_TAG, depot=$OL_IMG_TAG)"
   echo "--------------------------------------------------"
   docker-compose -f docker-compose/local.yml -f docker-compose/persistence.yml up pg mongo frontend backend depot
 elif [[ (! -z "$BE_IMG_TAG") && (-z "$FE_IMG_TAG") ]];
 then
   echo "* w/ backend=$BE_IMG_TAG, depot=$OL_IMG_TAG) (no frontend)"
   echo "--------------------------------------------------"
   docker-compose -f docker-compose/local.yml -f docker-compose/persistence.yml up pg mongo backend depot
 else
   echo "* persistence only"
   echo "--------------------------------------------------"
   export FE_IMG_TAG=
   export BE_IMG_TAG=
   export OL_IMG_TAG=
   docker-compose -f docker-compose/local.yml -f docker-compose/persistence.yml up --remove-orphans pg pginit mongo
 fi
fi