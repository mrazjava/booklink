package com.github.mrazjava.booklink.dataimport.openlibrary.model.work;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 */
@Data
public class TypeAuthor {

    @JsonIgnore
    String type;

    Author author;
}
