package com.github.mrazjava.booklink.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Author {

    @JsonIgnore
    String type;

    String role;

    Key author;
}
