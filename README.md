# Booklink
Cross reference of books.

## Components
Actual components live in their own repos. This is a root project with overview and high level utilities.
* Vue.js based [frontend](https://github.com/mrazjava/booklink-frontend-vue)
* Java based [backend](https://github.com/mrazjava/booklink-backend)

## Environments
Booklink is deployed in [AWS](https://aws.amazon.com/) where it lives in `live` and `pre`-release environments. There is also a `stg` 
(staging) environment but it is only available in sandbox locally.
* `live`
   - AWS, not setup yet
   - manual deploy (from pre-release tested AWS ECR docker image)
   - also available via sandbox
* `pre`
   - stable candidate releases (pre-release), hosted on AWS free tier [T2.micro](https://aws.amazon.com/ec2/instance-types/t2/)
   - docker image [web-master](https://github.com/mrazjava/booklink/packages/141719?version=latest) deployed via [task-definition](https://github.com/mrazjava/booklink/blob/master/.aws/web-pre.json) as [frontend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com/)
   - docker image [backend-master](https://github.com/mrazjava/booklink/packages/130548?version=latest) deployed via [task-definition](https://github.com/mrazjava/booklink/blob/master/.aws/backend-pre.json) as [backend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com:8888/actuator/info)
   - QA testing, live demos
   - backend deploy triggered by github action upon push to `master`
   - frontend deploy triggered by github action upon push to `master`
   - also available via sandbox
* `stg`
   - staged feature changes and bug fixes in `develop`
   - requires: git, docker, docker-compose
   - testing, safe environment for experimentation
   - sandbox only
* `development`
   - local machine development environment w/ Maven, Git, Docker, IDE, etc.
   - requires: git, maven, jdk 11, docker, docker-compose
   - programming of new features, bug fixing, depending on branch may be unstable
   - can built docker images and run them with sandbox

## Sandbox
The fastest way to try booklink:
```
./sandbox.sh [live | pre | stg | local | help]
```
Sandbox is based on `docker-compose` and so if you prefer to work with docker directly see `docker-compose` section.

As sandbox attempts to pull latest `live` and `pre` images, it may leave previously overriden 
image in the dangling state. This will happen frequently with pre-release images as they run off a `latest` tag. You 
may want to clean dangling images after running sandbox:
```
docker image prune -f
```

## docker-compose<sup>1</sup>
Composition is used as a convenience feature to quickly run (or try out) multiple docker images tuned for the desired 
application instance. In case of booklink, mainly frontend and backend tuned for localhost. No need to compile sources, 
manually build images or setup anything:
```
cd docker-compose/
docker-compose [-f live.yml | pre.yml] | stg.yml] up
```
Depending on which compose file you run off of, Docker will pull the latest image from github (or build one from sources) 
and run it on your machine. Backend will be available on port `8080`. Frontend will run on port `8090`.

Candidate release image is built off `master` branch. It should be solid, well tested, and is the same as what runs in the 
AWS cloud. However, it may not have the latest features. To try the latest stable version, tell docker-compose to run off a 
staging configuration built from `develop` branch (the `stg.yml` file).

Compose does not pull latest images once cached. To make sure compose always runs off of the latest image, tell it to 
`pull` first before going `up`:
```
docker-compose [-f pre.yml | stg.yml] pull [backend | frontend]
```
<sup>1</sup> | Requires [docker](https://docs.docker.com/install/) + [docker-compose](https://docs.docker.com/compose/install/) 
installation. On Ubuntu for example, this can be done with `sudo apt install docker-compose`, which installs 
docker-compose directly, and docker (`docker.io` package) indirectly since compose depends on docker. To avoid running 
docker as root, immediately after the installation, main user account should be added to `docker` group: 
`sudo usermod -aG docker ${USER}`.

## Branching / CI Pipeline
Work is done on a `feature/*` branch. Push to feature triggers build with unit tests. Feature is merged 
to `develop`. Merge to develop triggers a full test suite (unit/integration) and builds a snapshot docker 
image pushed to github [packages](https://github.com/mrazjava/booklink/packages). Release is made by merging `develop` into `master`.

* Direct commits to `develop` should be avoided.
* Direct commits to `master` should never happen except for hotfixes. If hotfix is applied to master, it is immediately backported to develop.

Merge to `master` triggers release CI pipeline which:

* builds application from master sources
* builds latest release docker image(s) based on source tree which triggered commit (eg: `booklink-backend`, `booklink-web`)
* pushes latest release docker image to [github packages](https://github.com/mrazjava/booklink/packages) (`web-master`,`backend-master`)
* pushes latest release docker image to AWS ECR (`latest` tag of the above)

`master` version of `booklink/docker-compose.yml` (project root) bootstraps app from release ready images (`-master:latest`). 
As such, image changes to this file from `develop` branch should not be merged.

## -WIP-

booklink
* master (the only branch)
- sandbox.sh
.. live [BE=latest|FE=latest]
    - default, runs most recent master tag
.. pre
    - runs :latest master
.. staging
    - runs :latest develop

booklink-backend
* master
- merge/push pushes latest master image and deploys pre-release with it
- tag pushes a final, version-tagged image to dockerhub and AWS ECR (no deployment)
* develop
- merge/push pushes latest develop image to dockerhub (no deployment)
* feature
- unit tests only

booklink-frontend-vue
* master
- merge pushes latest image and deploys pre-release (w/ latest)
- tag pushes final image (tagged) to dockerhub
* develop
