# Booklink Persistence w/ Docker
Each booklink persistence store runs int he standbox as a standalone docker container. These docker images are 
typically managed via docker compose.

## Postgres
PostgreSQL is the main system database which powers the backend. Account data and all the system information is stored 
in this database.
```
docker build -f Dockerfile.postgres -t mrazjava/booklink-postgres:202009-12.2 .
``` 

## Mongo
For technical details about the structure of mongo database see 
[booklink-openlibrary](https://github.com/mrazjava/booklink-openlibrary) project. Raw book sources against which 
backend lookups are issued.
```
docker build -f Dockerfile.mongo -t mrazjava/booklink-mongo:202009-4.4.0 .
``` 

This docker image is updated at most on the monthly basis since openlibrary dumps are issued on the monthly basis as 
well. The version schema is composed of a for digit year `YYYY` and a two digit month `MM`.

Spinning up mongo image directly:
```
docker run -p 27117:27017/tcp mrazjava/booklink-mongo:202009-4.4.0
```