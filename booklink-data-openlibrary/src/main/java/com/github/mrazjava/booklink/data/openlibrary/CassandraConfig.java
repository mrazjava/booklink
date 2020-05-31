package com.github.mrazjava.booklink.data.openlibrary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

/**
 * @author Adam Zimowski (<a href="mailto:adam.zimowski@freenetdigital.com">azimowski</a>)
 */
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:9042}")
    private int port;

    @Value("${spring.data.cassandra.keyspace:placeholder}")
    private String keySpace;

    @Value("${spring.data.cassandra.schema-action}")
    private String schemaAction;

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(schemaAction);
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }
}
