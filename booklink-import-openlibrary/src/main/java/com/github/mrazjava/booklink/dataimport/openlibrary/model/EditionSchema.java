package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mrazjava.booklink.dataimport.openlibrary.model.work.Key;
import com.github.mrazjava.booklink.dataimport.openlibrary.model.work.TypeValue;
import lombok.Data;

import java.util.List;

@Data
public class EditionSchema extends Key {

    private List<String> publishers;

    private TypeValue created;

    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @JsonProperty("isbn_13")
    private List<String> isbn13;

    @JsonProperty("number_of_pages")
    private Integer numberOfPages;

    @JsonProperty("physical_format")
    private String physicalFormat;

    @JsonProperty("last_modified")
    private String lastModified;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Key> authors;

    private String title;

    @JsonProperty("latest_revision")
    private Integer latestRevision;

    private List<Key> works;

    @JsonIgnore
    private Object type;

    private Integer revision;
}
