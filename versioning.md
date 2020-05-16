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