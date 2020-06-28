# Booklink Data: openlibrary.org
Implementation of core author, book and edition data based on openlibrary.org. Interface to query books exposed via 
REST.

## Tech Stack
* Spring Boot
* [Apache Kafka](https://kafka.apache.org/)
* [Apache Cassandra](https://cassandra.apache.org/)

## Bitnami Cassandra
Cassandra is provided via docker and can be started locally with `docker-compose up`. To troubleshoot cassandra, it's 
best to enter the running docker container directly:
```
docker exec -it <CONTAINER_ID> bash
```
Here are some useful commands to run from inside the cassandra docker container:
```
# determine the default data center name, if using NetworkTopologyStrategy, using nodetool status.
nodetool status

# start CQLSH shell
cqlsh -u cassandra -p cassandra
```

#### CQLSH>
```
CREATE KEYSPACE IF NOT EXISTS openlibrary WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };
```

## Notes
- [basic boot setup](https://bezkoder.com/spring-boot-cassandra-crud/)
- [cassandra docker image](https://hub.docker.com/r/bitnami/cassandra/)
- cassandra docker image: [create keyspace on startup](https://github.com/docker-library/cassandra/issues/104)
- [cassandra on minicube + boot](https://medium.com/@aamine/spring-data-for-cassandra-a-complete-example-3c6f7f39fef9)
- [DBeaver community Cassandra setup](https://medium.com/@raphaelrodrigues_74842/how-to-connect-cassandra-database-using-dbeaver-community-7d7b43a058e2)