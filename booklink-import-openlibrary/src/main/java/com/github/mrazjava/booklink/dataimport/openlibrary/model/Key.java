package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Key {

    private String key;
}
