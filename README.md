# Booklink
Cross reference of books.

## Sources
Component sources that make up Booklink live in their own repos. This is just an overview project which aggregates common 
documentation and high level utilities.
* Vue.js based [frontend](https://github.com/mrazjava/booklink-frontend-vue)
* Java based [backend](https://github.com/mrazjava/booklink-backend)

## Environments
Booklink is deployed in [AWS](https://aws.amazon.com/) where it lives in `live` and `pre` release environments.

* `live` frontend | backend .. (not setup yet)
   - manual deploy from pre-release tested aws [ecr](https://aws.amazon.com/ecr/) docker image
   - requires: internet connection only (or more if using sandbox)
* `pre` [frontend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com/) | [backend](http://ec2-3-124-3-167.eu-central-1.compute.amazonaws.com:8888/actuator/info)
   - stable candidate releases hosted on aws free tier [T2.micro](https://aws.amazon.com/ec2/instance-types/t2/)
   - docker image [booklink-frontend-vue](https://hub.docker.com/repository/docker/mrazjava/booklink-frontend-vue) auto deployed via ci [workflow](https://github.com/mrazjava/booklink-frontend-vue/blob/master/.github/workflows/pre-release.yml) and aws [task-definition](https://github.com/mrazjava/booklink-frontend-vue/blob/master/.aws/pre-release.json)
   - docker image [booklink-backend](https://hub.docker.com/repository/docker/mrazjava/booklink-backend) auto deployed via ci [workflow](https://github.com/mrazjava/booklink-backend/blob/master/.github/workflows/pre-release.yml) and aws [task-definition](https://github.com/mrazjava/booklink-backend/blob/master/.aws/pre-release.json)
   - QA testing, live demos
   - requires: internet connection only (or more if using sandbox)
* `stg` @[sandbox](https://github.com/mrazjava/booklink#sandbox)
   - staged feature changes and bug fixes in `develop` branch (candidate for merge to `master`)
   - requires: docker, docker-compose
* `dev`
   - local machine, active development environment w/ Vue CLI (+Node), Maven, Git, Docker, JDK, IDE, etc.
   - requires full set of dev tools (see respective project repo for details)
   - programming of new features, bug fixing, depending on branch may be unstable
   - `yarn serve` (frontend), `mvn clean spring-boot:run` (backend)

## Sandbox
The fastest way to try booklink locally:
```
./sandbox.sh [live | pre | stg | help]
```
Frontend will run on port `8090`, backend on `8080`. Sandbox is based on [docker compose](https://docs.docker.com/compose/) and so if you prefer to work 
with docker directly see the docker-compose [section](https://github.com/mrazjava/booklink#docker-compose3).

As sandbox always attempts to pull from dockerhub every time, it may leave previously overridden image in the dangling state. This will happen frequently with pre-release and staging images as they run off fixed tags which are simply overriden. You may want to clean dangling images after running sandbox:
```
docker image prune -f
```
Perhaps the nicest feature of sandbox is the ability to easily run archived<sup>1</sup> releases or combine<sup>2</sup> archived frontend and backend releases which were never deployed together.

<sup>1</sup> | Archived release is a version tagged docker image which at some point in the past was deployed live but it was displaced by newer version and no longer runs in any environment. Archived release could also be a version tagged release which for whatever reason was never deployed live (last minute skip, etc).

<sup>2</sup> | At any point there is always one specific version of frontend and backend deployed together (eg: 0.5.0 FE and 0.8.0 BE). As releases grow there will be many versions of both that were never deployed or even tested together. While running such combinations will often be impractical, it may be a lot of fun!

## docker-compose<sup>3</sup>
Composition is used as a convenience feature to quickly run (or try out) multiple docker images tuned for the desired 
application instance. In case of booklink, mainly frontend and backend tuned for localhost. No need to compile sources, 
manually build images or setup anything:
```
cd docker-compose/
docker-compose -f [live.yml | pre.yml] | stg.yml] up
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
be started via `yarn serve`.

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
 * IS NOT deployed to Amazon LIVE env automatically; however, it will be immediately available via `sandbox`

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
