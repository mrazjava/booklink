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

    private List<String> wikidata;

    private List<String> amazon;

    private List<String> google;

    private List<String> dnb;

    @JsonProperty("project_gutenberg")
    private List<String> projectGutenberg;

    @JsonProperty("depósito_legal")
    private List<String> depositoLegal;

    @JsonProperty("hathi_trust")
    private List<String> hathiTrust;

    @JsonProperty("alibris_id")
    private List<String> alibrisId;

    private List<String> bcid;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Key> works;
}
