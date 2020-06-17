package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

abstract class BaseSchema extends Key {

    @JsonProperty("lc_classifications")
    private List<String> lcClassifications;
}
