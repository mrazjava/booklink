package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
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

    @JsonProperty("copyright_date")
    private String copyrightDate;

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

    private String firstSentence;

    @JsonProperty("ia_loaded_id")
    private List<String> iaLoadedId;

    @JsonProperty("ia_box_id")
    private List<String> iaBoxId;

    @JsonProperty("local_id")
    private List<String> localId;

    private List<String> location;

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

    @JsonProperty("full_title")
    private String fullTitle;

    @JsonProperty("work_titles")
    private List<String> workTitles;

    @JsonProperty("title_prefix")
    private String titlePrefix;

    @JsonProperty("work_title")
    private List<String> workTitle;

    @JsonProperty("other_titles")
    private List<String> otherTitles;

    private String subtitle;

    @JsonProperty("coverimage")
    private String coverImage;

    @JsonProperty("scan_records")
    private List<Key> scanRecords;

    @JsonProperty("scan_on_demand")
    private Boolean scanOnDemand;

    private String notes;

    @JsonProperty("latest_revision")
    private Integer latestRevision;

    @JsonProperty("edition_name")
    private String editionName;

    private List<Long> covers;

    @JsonProperty("translated_from")
    private List<Key> translatedFrom;

    @JsonProperty("translation_of")
    private String translationOf;

    private List<String> series;

    @JsonProperty("oclc_numbers")
    private List<String> oclcNumbers;

    @JsonProperty("oclc_number")
    private List<String> oclcNumber;

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

    @JsonSetter("first_sentence")
    public void setJsonFirstSentence(JsonNode json) {
        if(json != null) {
            String text;
            if (json.isTextual()) {
                text = json.asText();
            } else {
                text = json.get("value").asText();
            }
            firstSentence = text;
        }
    }

    @JsonSetter("toc")
    public void setJsonExcerpts(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(toc)) {
                toc = new LinkedList<>();
            }
            for (JsonNode jn : json) {
                toc.add(jn.isTextual() ? new TableOfContent(jn.asText()) : produceToc(jn));
            }
        }
    }

    private TableOfContent produceToc(JsonNode json) {

        TableOfContent toc = new TableOfContent();

        if(json.has("class")) {
            toc.setClazz(json.get("class").asText());
        }
        if(json.has("label")) {
            toc.setLabel(json.get("label").asText());
        }
        if(json.has("label")) {
            toc.setLevel(Integer.valueOf(json.get("label").asText()));
        }
        if(json.has("pagenum")) {
            toc.setLabel(json.get("pagenum").asText());
        }
        if(json.has("title")) {
            toc.setLabel(json.get("title").asText());
        }
        if(json.has("key")) {
            Key type = new Key();
            type.setKey(json.get("title").asText());
            toc.setType(type);
        }
        if(json.has("value")) {
            toc.setValue(json.get("value").asText());
        }

        return toc;
    }
}
