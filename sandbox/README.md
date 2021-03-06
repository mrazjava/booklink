## Sandbox
Prerequisites:
```
sudo apt-get install git docker-compose
```
Run booklink locally with ability to simulate any environment.
```
./sandbox.sh [live | pre | stg | local | help]
```
Frontend will run on port `8090`, backend on `8080`, depot on `8070`, PostgreSQL on `5432` and Mongo on port `21017`.

Sandbox is based on [docker compose](https://docs.docker.com/compose/) so everything that sandbox does, can be done 
with docker directly at a cost of less automation. See docker-compose [section](#docker-compose) if you 
prefer to work that way.

As sandbox always attempts to pull from dockerhub, it may leave previously overridden image in the dangling 
state. This will happen frequently with pre-release and staging images as they run off fixed tags which are simply 
overridden. You may want to clean dangling images after running sandbox:
```
docker image prune -f
```
Perhaps the nicest feature of sandbox is the ability to easily run releases never officially deployed or even tested 
together before. Such are the archives<sup>1</sup>. While running archived combinations will often be impractical, it 
may be a lot of fun! On the other hand, `local` mode (for developers) is quite useful. In local mode it's possible to 
run experimental (cutting edge) branch of one component (say the backend) against a live, pre-release or staged version 
of another (eg: the frontend). 

Regardless of environment chosen, sandbox will always start the full persistence layer. All PostgreSQL environment 
databases are always available regardless of which sandbox environment is launched. Mongo on the other hand is shared 
across all environemnts.

<sup>1</sup> | Archived release is a version tagged docker image which at some point in the past was deployed live but it was displaced by newer version and no longer runs in any environment. Archived release could also be a version tagged release which for whatever reason was never deployed live (last minute skip, etc).

### healthcheck
Just as in AWS where Amazon cloud is configured for pre-release with healthcheck monitors, sandbox has a similar 
setup. Once started, you can monitor sandbox with:
```
docker events --filter event=health_status
```
If we shut down persistence container to simulate database outage, docker would start logging health errors similar 
to this one:
```
2020-04-06T10:26:57.476218502+02:00 container health_status: unhealthy 9c8c59523f46d583e1043b460590b4c407fb1c829874af04d56a1b23e98ae816 (com.docker.compose.config-hash=696c78526debb2cfe9011743066e99f6d35c8e28374abe13258e5627791487ee, com.docker.compose.container-number=1, com.docker.compose.oneoff=False, com.docker.compose.project=dockercompose, com.docker.compose.service=backend, com.docker.compose.version=1.15.0, image=docker.io/mrazjava/booklink-backend:develop, name=dockercompose_backend_1)
```

### Examples
\# 1
Try the entire solution using latest released version:

```
./sandbox.sh live
```

\# 2

Typical for frontend development, we would start everything except frontend. Specifically:
- backend app (staging - current release under dev)
- backend db infostructure (postgres; latest stable release - each env has its own schema)
- depot app (latest stable release)
- depot db (mongo; specific version from december 2020)

Mongo selection is optional. If omitted, latest will be used.

```
./sandbox.sh local -b -m 202012
```

\# 3

Similar to #2, but if there are staged depot changes affecting the backend, we need to 
use snapshot depot as well:

```
./sandbox.sh local -b -d develop -m 202012
```


## No Sandbox
More adventurous souls can run booklink images via [docker-compose](https://github.com/mrazjava/booklink#docker-compose) 
or even directly with docker. The included `env` file drives the config of an image we spin; at minimum, it should 
override database URL to point at the postgres container service (`APP_BE_DB_URL=jdbc:postgresql://pg:5432/booklink`). 
Here we run a `stg` image reachable on port `8888`:
```
docker run -p 8888:8080 --network booklinkbackend_default --env-file=/tmp/env mrazjava/booklink-backend:develop
```

## docker-compose
Composition is used as a convenience feature to quickly run (or try out) multiple docker images tuned for the desired 
application instance. In case of booklink, mainly frontend and backend tuned for localhost. No need to compile sources, 
manually build images or setup anything:
```
cd docker-compose/
docker-compose -f [live.yml | pre.yml] | stg.yml | local.yml] up
```
Candidate release image is built off `master` branch. It should be solid, well tested, and is the same as what runs in the 
AWS cloud. However, it may not have the latest features. To try the latest stable version, tell docker-compose to run off a 
staging configuration built from `develop` branch (the `stg.yml` file).

Compose does not pull latest images once cached. To make sure compose always runs off of the latest image, tell it to 
`pull` first before going `up`:
```
docker-compose [-f pre.yml | stg.yml] pull [backend | frontend]
```

Compose files within project repos are designed to bootstrap dependencies for that project only without the project 
itself. For example, `docker-compose.yml` in `booklink-frontend-vue` will bootstrap the backend, database and 
everything else necessary for the frontend to operate, but without the frontend which for development purposes should 
be started via `yarn serve`. Alternatively, dependencies can be bootstrapped with slim `local` sandbox mode (no frontend 
or backedn) - see sandbox help for details.

<sup>3</sup> | Requires [docker](https://docs.docker.com/install/) + [docker-compose](https://docs.docker.com/compose/install/) 
installation. On Ubuntu for example, this can be done with `sudo apt install docker-compose`, which installs 
docker-compose directly, and docker (`docker.io` package) indirectly since compose depends on docker. To avoid running 
docker as root, immediately after the installation, main user account should be added to `docker` group: 
`sudo usermod -aG docker ${USER}`.

## Useful Commands
Get IP of a running docker container from the host:
```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <CONTAINER_ID>
```
Connect to sandbox PostgreSQL via command line client:
```
psql -h localhost -d booklink -U bookworm -p 5433
```
Delete volume used by specific docker container:
```
docker rm -v <CONTAINER_ID>
```
A more manual way of doing the above:
```
docker ps -a # get container id
docker inspect -f '{{ .Mounts }}' <CONTAINER_ID> # get volume id
docker rm <CONTAINER_ID> # remove container otherwise rm volume fails
docker volume rm <VOLUME_ID>
```

## Notes
A following error may occur if starting containers after long period of inactivity:
```
ERROR: for frontend  Cannot start service frontend: network 451b39b5d69daf2a76ecc50d1e0ed059229b55678a2ac8081cc6b82c643de26b not found
ERROR: for backend  Cannot start service backend: network 451b39b5d69daf2a76ecc50d1e0ed059229b55678a2ac8081cc6b82c643de26b not found
ERROR: Encountered errors while bringing up the project.
```
Clean up (`docker-compose down`) or re-create containers.