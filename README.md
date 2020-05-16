# Booklink
Cross reference of books.

This is an overview project which aggregates common documentation and high level utilities.

## Sub Projects
General utilities are maintaned here as sub projects.

##### booklink-data-openlibrary
Default data source for books and authors based on [openlibrary](http://openlibrary.org).

## Application Sources
Component sources that make up Booklink live in their own repos.
* Vue.js based [frontend](https://github.com/mrazjava/booklink-frontend-vue)
* Java based [backend](https://github.com/mrazjava/booklink-backend)

## Environments
Booklink is deployed in [AWS](https://aws.amazon.com/) where it lives in `live` and `pre` release environments.

* `live` frontend | backend .. (not setup yet)
   - manual deploy from pre-release tested aws [ecr](https://aws.amazon.com/ecr/) docker image
* `pre` [frontend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com/) | [backend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com:8888/actuator/info)
   - stable candidate releases hosted on aws free tier [T2.micro](https://aws.amazon.com/ec2/instance-types/t2/)
   - docker image [booklink-frontend-vue](https://hub.docker.com/repository/docker/mrazjava/booklink-frontend-vue) auto deployed via ci [workflow](https://github.com/mrazjava/booklink-frontend-vue/blob/master/.github/workflows/pre-release.yml) and aws [task-definition](https://github.com/mrazjava/booklink-frontend-vue/blob/master/.aws/pre-release.json)
   - docker image [booklink-backend](https://hub.docker.com/repository/docker/mrazjava/booklink-backend) auto deployed via ci [workflow](https://github.com/mrazjava/booklink-backend/blob/master/.github/workflows/pre-release.yml) and aws [task-definition](https://github.com/mrazjava/booklink-backend/blob/master/.aws/pre-release.json)
   - QA testing, live demos
* `stg` @[sandbox](https://github.com/mrazjava/booklink#sandbox)
   - staged feature changes and bug fixes in `develop` branch (candidate for merge to `master`)
* `local`
   - active development environment w/ Vue CLI (+Node), Maven, Git, Docker, JDK, IDE, etc.
   - programming of new features, bug fixing, depending on branch may be unstable
   - `yarn serve` (frontend), `mvn clean spring-boot:run` (backend)

## Sandbox
The fastest way to try booklink locally:
```
./sandbox.sh [live | pre | stg | local | help]
```
Frontend will run on port `8090`, backend on `8080`, PostgreSQL on `5432` and pgAdmin4 on port `5500`.

Sandbox is based on [docker compose](https://docs.docker.com/compose/) so everything that sandbox does, can be done 
with docker directly at a cost of less automation. See docker-compose [section](https://github.com/mrazjava/booklink#docker-compose3) if you 
prefer to work that way.

As sandbox always attempts to pull from dockerhub every time, it may leave previously overridden image in the dangling 
state. This will happen frequently with pre-release and staging images as they run off fixed tags which are simply 
overriden. You may want to clean dangling images after running sandbox:
```
docker image prune -f
```
Perhaps the nicest feature of sandbox is the ability to easily run releases never officially deployed or even tested together before. Such are the archives<sup>1</sup>. While running archived combinations will often be impractical, it may be a lot of fun! On the other hand, `local` mode (for developers) is quite useful. In local mode it's possible to run experimental (cutting edge) branch of one component (say the backend) against a live, pre-release or staged version of another (eg: the frontend). 

Regardless of environment chosen, sandbox will always start PostgreSQL persistence layer along with the pg4Admin. All PostgreSQL environment databases are always available regardless of which sandbox environment is launched.

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

## No Sandbox
Use included `env` file to drive config of an image you want to spin. For example, here we run a `pre` image reachable 
on port `8888`:
```
docker run -p8888:8080 --env-file=env mrazjava/booklink-backend:master
```

## docker-compose<sup>2</sup>
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

## Branching / CI Pipeline
Work is typically done on a `feature/*` or `bug/*` branch. Push to such branch does not trigger a CI pipeline. It is responsibility of a committer to ensure that branch is clean and ready to pass CI pipeline after merge. Custom branches are tested locally on the developer's workstation. They are eventually merged to `develop`.

* Direct commits to `develop` should be avoided. Exceptions could be version bumps, minor documentation updates, etc.

Merge to `develop` triggers CI pipeline which:

* runs a full test suite (unit/integration)
* builds latest **snapshot** docker image tagged as `:develop` and pushes it to [dockerhub](https://hub.docker.com/search?q=mrazjava%2Fbooklink&type=image) only
* sandbox `stg` will immediately use the new dockerhub image on next run

When staged changes are ready for general QA, merge from `develop` into `master` occurs.

* Direct commits to `master` should never happen except for hotfixes. If hotfix is applied to `master`, it is immediately backported to `develop`.

Merge to `master` triggers release CI pipeline which:

* builds application from `master` sources
* assembles latest **release** docker image tagged as `:master` and pushes it to [dockerhub](https://hub.docker.com/search?q=mrazjava%2Fbooklink&type=image) and Amazon ECR
* AWS `pre` release environment is automatically re-deployed using the `master` image just pushed
* sandbox `pre` will immediately use the new dockerhub image on next run

Github [release](https://help.github.com/en/github/administering-a-repository/about-releases) triggers CI pipeline which:

 * builds the finalized version of application
 * assembles the finalized docker image tagged with a version defined in the release (eg: `v0.1.0`)
 * pushes the finalized release docker image to dockerhub and amazon ecr
 * IS NOT deployed to Amazon `live` env automatically; however, it will be immediately available via `sandbox`

While technically release could be made off any branch, it is always done off `master`. Version is always prefixed with a `v` just as github and versioning semantics suggest.

## Versioning
Versions are bumped up manually, in `develop`, immediately after tagging `master` branch:

* for `booklink-backend` in `pom.xml`
* for `booklink-frontend-vue` in `package.json`

A bump by default occurs to the minor number, eg: `X.Y.Z` where `X` would have been a major release, `Y` a minor 
release, and `Z` a hotfix/patch release. If the scope of release changes, version can be adjusted later on. Tagging 
master does not necessailry imply live deploy which is done manually and therefore may bypass specific tagged release. 
Therefore it is possibly to have a tagged, versioned release without it ever being deployed live to the AWS cloud. Such 
release would still be available in the `live` sandbox environment though. In anycase, for these reasons we bump 
versions **after** tagging master.

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
