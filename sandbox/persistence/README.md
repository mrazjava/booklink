# Booklink Persistence w/ Docker
Each booklink persistence store runs in the sandbox as a standalone docker container. These docker images are 
typically managed via docker compose.

## Postgres
PostgreSQL is the main system database which powers the backend. User data and all other system information is stored 
in this database, including book references fetched from mongo.
```
docker build -f Dockerfile.postgres -t mrazjava/booklink-postgres:202009-12.2 .
``` 

## Mongo
The mongo database provides raw book sources against which backend lookups are issued. For technical details about 
the structure of mongo database see [booklink-openlibrary](https://github.com/mrazjava/booklink-openlibrary) project.

Steps to prepare fresh mongo image:
1) Run the import on the latest openlibrary.org data dumps
2) Dump mongo image created from step 1 (see openlibrary integration README)
3) Copy `openlibrary-mongo.artchive` dump file to this directory
4) Execute docker build command below:
```
docker build -f Dockerfile.mongo -t mrazjava/booklink-mongo:YYYYMM-4.4.0 .
``` 

This docker image is updated at most on the monthly basis since openlibrary dumps are issued on the monthly basis as 
well. The version schema is composed of a for digit year `YYYY` and a two digit month `MM`.

Spinning up mongo image directly:
```
docker run -p 27117:27017/tcp mrazjava/booklink-mongo:YYYYMM-4.4.0
```