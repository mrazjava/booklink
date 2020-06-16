package com.github.mrazjava.booklink.dataimport.openlibrary.model;

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
