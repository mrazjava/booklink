package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Identifiers {

    private List<String> goodreads;

    private List<String> librarything;

    @JsonAlias("library_of_congress_classification_(lcc)")
    private List<String> lccn;

    private List<String> wikidata;

    @JsonProperty("british_national_bibliography")
    private List<String> britishNationalBibliography;

    @JsonProperty("british_library")
    private List<String> britishLibrary;

    @JsonProperty("bayerische_staatsbibliothek")
    private List<String> bayerischeBibliothek;

    private List<String> amazon;

    @JsonProperty("amazon.de_asin")
    private List<String> amazonDe;

    @JsonProperty("amazon.co.uk_asin")
    private List<String> amazonUk;

    private List<String> google;

    private List<String> dnb;

    private List<String> nla;

    private List<String> issn;

    private List<String> harvard;

    @JsonProperty("bodleian,_oxford_university")
    private List<String> oxford;

    private List<String> overdrive;

    private List<String> shelfari;

    private List<String> ean;

    @JsonProperty("project_gutenberg")
    private List<String> projectGutenberg;

    @JsonProperty("depósito_legal")
    private List<String> depositoLegal;

    @JsonProperty("hathi_trust")
    private List<String> hathiTrust;

    @JsonProperty("alibris_id")
    private List<String> alibrisId;

    private List<String> bcid;

    @JsonProperty("zdb-id")
    private List<String> zdbId;

    private List<String> isfdb;

    private List<String> libris;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Key> works;

    private List<String> ulrls;
}
