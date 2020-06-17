package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class EditionSchema extends BaseSchema {

    private List<String> publishers;

    private TypeValue created;

    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @JsonProperty("isbn_13")
    private List<String> isbn13;

    @JsonProperty("isbn_invalid")
    private List<String> isbnInvalid;

    @JsonProperty("number_of_pages")
    private Integer numberOfPages;

    @JsonProperty("physical_format")
    private String physicalFormat;

    @JsonProperty("last_modified")
    private TypeValue lastModified;

    private Object classifications;

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

    private String description;

    @JsonProperty("first_sentence")
    private TypeValue firstSentence;

    @JsonProperty("ia_loaded_id")
    private List<String> iaLoadedId;

    @JsonProperty("ia_box_id")
    private List<String> iaBoxId;

    @JsonProperty("local_id")
    private List<String> localId;

    private String weight;

    @JsonProperty("physical_dimensions")
    private String physicalDimentions;

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

    private List<String> genres;

    private List<String> lccn;

    @JsonProperty("source_records")
    private List<String> sourceRecords;

    private List<Key> authors;

    private String title;

    @JsonProperty("title_prefix")
    private String titlePrefix;

    @JsonProperty("other_titles")
    private List<String> otherTitles;

    @JsonProperty("work_title")
    private List<String> workTitle;

    private String subtitle;

    private String notes;

    @JsonProperty("latest_revision")
    private Integer latestRevision;

    @JsonProperty("edition_name")
    private String editionName;

    private List<Long> covers;

    private List<String> series;

    @JsonProperty("oclc_numbers")
    private List<String> oclcNumbers;

    private List<Key> works;

    private List<Key> languages;

    private List<String> subjects;

    private Identifiers identifiers;

    @JsonIgnore
    private Object type;

    private Integer revision;

    private List<Contributor> contributors;

    @JsonProperty("table_of_contents")
    private List<TableOfContent> toc;

    @JsonSetter("notes")
    public void setJsonNotes(JsonNode json) {
        if(json != null) {
            String text;
            if (json.isTextual()) {
                text = json.asText();
            } else {
                text = json.get("value").asText();
            }
            notes = text;
        }
    }

    @JsonSetter("description")
    public void setJsonDescription(JsonNode json) {
        if(json != null) {
            String text;
            if (json.isTextual()) {
                text = json.asText();
            } else {
                text = json.get("value").asText();
            }
            description = text;
        }
    }
}
