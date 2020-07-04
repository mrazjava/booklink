package com.github.mrazjava.booklink.dataimport.openlibrary;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PlainTextAuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Zimowski (<a href="mailto:adam.zimowski@freenetdigital.com">azimowski</a>)
 */
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points:localhost}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:9042}")
    private int port;

    @Value("${spring.data.cassandra.keyspace:openlibrary}")
    private String keyspace;

    @Value("${spring.data.cassandra.schema-action}")
    private String schemaAction;

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.password}")
    private String password;

    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(getContactPoints());
        bean.setUsername(username);
        bean.setPassword(password);
        return bean;
    }

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
        return keyspace;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    protected AuthProvider getAuthProvider() {
        return new PlainTextAuthProvider(username, password);
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification keyspaceSpecification = CreateKeyspaceSpecification.createKeyspace(getKeyspaceName());
        DataCenterReplication dcr = DataCenterReplication.of("dc1", 3L);
        keyspaceSpecification.ifNotExists(true).withNetworkReplication(dcr);
        return keyspaceSpecification;
    }
}
