package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Identifiers {

    private List<String> goodreads;

    private List<String> librarything;

    private List<String> lccn;

    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Key> works;
}
