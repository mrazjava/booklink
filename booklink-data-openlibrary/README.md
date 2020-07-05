# Booklink Data: openlibrary.org
Implementation of core author, book and edition data based on openlibrary.org. Interface to query books exposed via 
REST.

## Tech Stack
* Spring Boot
* MongoDb

## Mongo
Example queries:
```
db.getCollection('editions').find({ authors: ["OL1880710A"]})
db.getCollection('editions').find({ authors: null})
db.getCollection('editions').count()
db.getCollection('editions').count({ authors: null})
```