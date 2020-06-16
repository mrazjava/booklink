package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private TypeValue lastModified;

    private List<String> contributions;

    @JsonProperty("uri_descriptions")
    private List<String> uriDescriptions;

    private List<String> uris;

    private List<String> url;

    private String ocaid;

    private String pagination;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("subject_time")
    private List<String> subjectTime;

    @JsonProperty("by_statement")
    private String byStatement;

    @JsonProperty("subject_place")
    private List<String> subjectPlace;

    @JsonProperty("publish_places")
    private List<String> publishPlaces;

    @JsonProperty("publish_country")
    private String publishCountry;

    @JsonProperty("dewey_decimal_class")
    private List<String> deweyDecimalClass;

    private List<String> lccn;

    @JsonProperty("source_records")
    private List<String> sourceRecords;

    private List<Key> authors;

    private String title;

    @JsonProperty("other_titles")
    private List<String> otherTitles;

    private String subtitle;

    private TypeValue notes;

    @JsonProperty("latest_revision")
    private Integer latestRevision;

    @JsonProperty("edition_name")
    private String editionName;

    private List<Long> covers;

    private List<String> series;

    @JsonProperty("lc_classifications")
    private List<String> classifications;

    @JsonProperty("oclc_numbers")
    private List<String> oclcNumbers;

    private List<Key> works;

    private List<Key> languages;

    private List<String> subjects;

    private Identifiers identifiers;

    @JsonIgnore
    private Object type;

    private Integer revision;
}
