# Booklink Persistence w/ Docker
Each booklink persistence store runs in the sandbox as a standalone docker container. These docker images are 
typically managed via docker compose.

## Postgres
PostgreSQL is the main system database which powers the backend. User data and all other system information is stored 
in this database, including book references fetched from mongo.

```
docker build -f Dockerfile.postgres -t mrazjava/booklink-postgres .
``` 
FYI: postgres image is simply on a `latest` tag.

In sandbox, environments are bound to a schema on a single `booklink` database. Schema initialzation is handled via 
another small container:
```
docker build -f Dockerfile.sandbox-pg-init -t mrazjava/booklink-sandbox-pg-init .
```
FYI: like postgres image, sandbox schema initializer is also bound to a `latest` tag.

## Mongo
The mongo database provides raw book sources against which backend lookups are issued. For technical details about 
the structure of mongo database see [booklink-openlibrary](https://github.com/mrazjava/booklink-openlibrary) project.

Steps to prepare fresh mongo image:
1) Download latest [ol_dump](https://archive.org/details/ol_exports?sort=-publicdate) files. After clicking latest 
month, under the "Download Options" expand GZIP and download authors, works and editions archives.
2) Run the import on the latest openlibrary.org data dumps
3) Dump mongo image created from step 1 (see openlibrary integration README)
4) Copy `openlibrary-mongo.artchive` dump file to this directory
5) Execute docker build command below:
```
docker build -f Dockerfile.mongo -t mrazjava/booklink-mongo:YYYYMM-4.4.0 .
```
Dump is part of a docker image. When image is started for the first time, dump is imported and database created. 

This docker image is updated on the monthly to match openlibrary.org dump release cycle. The version schema is 
composed of a for digit year `YYYY` and a two digit month `MM`.

Spinning up mongo image directly:
```
docker run -p 27117:27017/tcp mrazjava/booklink-mongo:YYYYMM-4.4.0
```