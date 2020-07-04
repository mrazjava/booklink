package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.util.List;

abstract class BaseSchema {

    @PrimaryKey
    @JsonProperty
    private String key;

    @Transient
    @JsonAlias("lc_classification")
    @JsonProperty("lc_classifications")
    private List<String> lcClassifications;

    @JsonSetter("key")
    public void setKey(JsonNode jsonNode) {
        String val = jsonNode.textValue();
        this.key = val.substring(val.lastIndexOf("/")+1);
    }
}
