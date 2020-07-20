package com.github.mrazjava.booklink.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TypeValue {

    @JsonIgnore
    private String type;

    private String value;
}