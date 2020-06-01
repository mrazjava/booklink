package com.github.mrazjava.booklink.data.openlibrary;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PlainTextAuthProvider;
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

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.password}")
    private String password;


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

    @Override
    protected AuthProvider getAuthProvider() {
        return new PlainTextAuthProvider(username, password);
    }
}
