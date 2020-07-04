package com.github.mrazjava.booklink.dataimport.openlibrary.repository;

import com.github.mrazjava.booklink.dataimport.openlibrary.model.EditionSchema;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface EditionRepository extends CassandraRepository<EditionSchema, String> {

    Optional<EditionSchema> findById(String id);
}
