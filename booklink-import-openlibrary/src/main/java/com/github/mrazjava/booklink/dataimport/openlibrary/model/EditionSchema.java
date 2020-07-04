package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@JsonIgnoreProperties({"m", "type"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("edition")
public class EditionSchema extends BaseSchema {

    @Transient
    private List<String> authors; // IDs

    @Transient
    private List<Key> works; // IDs

    @Transient
    private String price;

    @Transient
    private Key edition;

    @Transient
    private String code;

    @Transient
    @JsonProperty("openlibrary")
    private String openLibrary;

    @Transient
    private List<String> publishers;

    @Transient
    private TypeValue created;

    @Transient
    @JsonProperty("remote_ids")
    private RemoteIds remoteIds;

    @Transient
    private Dimensions dimensions;

    @Transient
    private String publisher;

    @Transient
    private TypeValue body;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "bookweight")
    @JsonProperty("bookweight")
    private Weight bookWeight;

    @Transient
    @JsonProperty("coverid")
    private String coverId;

    @Transient
    private List<String> isbn;

    @Transient
    @JsonProperty("isbn_10")
    private List<String> isbn10;

    @Transient
    @JsonProperty("isbn_13")
    private List<String> isbn13;

    @Transient
    @JsonProperty("original_isbn")
    private String originalIsbn;

    @Transient
    @JsonProperty("isbn_invalid")
    private List<String> isbnInvalid;

    @Transient
    @JsonProperty("isbn_odd_length")
    private List<String> isbnOddLength;

    @Transient
    @JsonProperty("library_of_congress_name")
    private String libraryOfCongressName;

    @Transient
    @JsonProperty("purchase_url")
    private List<String> purchaseUrl;

    @Transient
    @JsonProperty("number_of_pages")
    private Integer numberOfPages;

    @Transient
    @JsonProperty("physical_format")
    private String physicalFormat;

    @Transient
    @JsonProperty("last_modified")
    private TypeValue lastModified;

    @Transient
    @JsonProperty("copyright_date")
    private String copyrightDate;

    @Transient
    private Object classifications;

    @Transient
    private TypeValue macro;

    @Transient
    private List<String> contributions;

    @Transient
    private List<String> collections;

    @Transient
    @JsonProperty("uri_descriptions")
    private List<String> uriDescriptions;

    @Transient
    private List<String> uris;

    @Transient
    private List<String> url;

    @Transient
    @JsonProperty("download_url")
    private List<String> downloadUrl;

    private String name;

    private String create;

    private String ocaid;

    private String pagination;

    @Transient
    @JsonProperty("publish_date")
    private String publishDate;

    @Transient
    @JsonAlias("subject_time")
    @JsonProperty("subject_times")
    private List<String> subjectTimes;

    @Transient
    private String description;

    @Transient
    private String firstSentence;

    @Transient
    private List<String> iaLoadedIds;

    @Transient
    private List<String> iaBoxIds;

    @Transient
    @JsonProperty("local_id")
    private List<String> localId;

    @Transient
    @JsonProperty("ia_id")
    private String iaId;

    @Transient
    private List<String> location;

    @Transient
    private String weight;

    @Transient
    @JsonProperty("physical_dimensions")
    private String physicalDimentions;

    @Transient
    @JsonProperty("by_statement")
    private String byStatement;

    @Transient
    @JsonAlias("subject_place")
    @JsonProperty("subject_places")
    private List<String> subjectPlaces;

    @Transient
    @JsonProperty("subject_people")
    private List<String> subjectPeople;

    @Transient
    @JsonProperty("publish_places")
    private List<String> publishPlaces;

    @Transient
    @JsonProperty("publish_country")
    private String publishCountry;

    @Transient
    @JsonProperty("dewey_decimal_class")
    private List<String> deweyDecimalClass;

    @Transient
    private List<String> genres;

    @Transient
    private List<String> lccn;

    @Transient
    @JsonProperty("source_records")
    private List<String> sourceRecords;

    @Transient
    @JsonProperty("author_names")
    private List<String> authorNames;

    @Transient
    private List<Link> links;

    @Transient
    private String title;

    @Transient
    @JsonProperty("full_title")
    private String fullTitle;

    @Transient
    @JsonAlias("work_title")
    @JsonProperty("work_titles")
    private List<String> workTitles;

    @Transient
    @JsonProperty("title_prefix")
    private String titlePrefix;

    @Transient
    @JsonProperty("other_titles")
    private List<String> otherTitles;

    @Transient
    private String subtitle;

    @Transient
    @JsonProperty("coverimage")
    private String coverImage;

    @Transient
    @JsonProperty("scan_records")
    private List<Key> scanRecords;

    @Transient
    @JsonProperty("scan_on_demand")
    private Boolean scanOnDemand;

    @Transient
    private String notes;

    @Transient
    @JsonProperty("latest_revision")
    private Integer latestRevision;

    @Transient
    @JsonProperty("edition_name")
    private String editionName;

    @Transient
    private List<Long> covers;

    @Transient
    @JsonProperty("translated_from")
    private List<Key> translatedFrom;

    @Transient
    @JsonProperty("translation_of")
    private String translationOf;

    @Transient
    private List<String> series;

    @Transient
    @JsonAlias("oclc_number")
    @JsonProperty("oclc_numbers")
    private List<String> oclcNumbers;

    @Transient
    @JsonAlias("language")
    private List<String> languages;

    @Transient
    @JsonProperty("language_code")
    private String languageCode;

    @Transient
    private List<String> subjects;

    @Transient
    private Identifiers identifiers;

    @Transient
    @JsonProperty("by_statements")
    private String byStatements;

    @Transient
    private Integer revision;

    @Transient
    private List<Contributor> contributors;

    @Transient
    @JsonProperty("table_of_contents")
    private List<TableOfContent> toc;

    @Transient
    private List<Volume> volumes;

    @Transient
    @JsonProperty("volume_number")
    private Integer volumeNumber;


    @JsonSetter("languages")
    public void setLanguages(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(languages)) {
                languages = new LinkedList<>();
            }
            if(!json.isArray()) {
                languages.add(fetchKey(json));
            }
            else {
                for(JsonNode jn : json) {
                    languages.add(fetchKey(jn));
                }
            }
        }
    }

    @JsonSetter("collections")
    public void setCollections(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(collections)) {
                collections = new LinkedList<>();
            }
            if(!json.isArray()) {
                collections.add(fetchKey(json));
            }
            else {
                for(JsonNode jn : json) {
                    collections.add(fetchKey(jn));
                }
            }
        }
    }

    @JsonSetter("authors")
    public void setAuthors(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(authors)) {
                authors = new LinkedList<>();
            }
            if(!json.isArray()) {
                authors.add(fetchKey(json));
            }
            else {
                for(JsonNode jn : json) {
                    authors.add(fetchKey(jn));
                }
            }
        }
    }

    private String fetchKey(JsonNode json) {
        String text = json.has("key") ? json.get("key").asText() : json.asText();
        return text.contains("/") ? text.substring(text.lastIndexOf("/") + 1) : text;
    }

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

    @JsonSetter("ia_loaded_id")
    public void setIaLoadedIds(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(iaLoadedIds)) {
                iaLoadedIds = new LinkedList<>();
            }
            if(json.isTextual()) {
                iaLoadedIds.add(json.asText());
            }
            else {
                for (JsonNode jn : json) {
                    iaLoadedIds.add(jn.asText());
                }
            }
        }
    }

    @JsonSetter("ia_box_id")
    public void setIaBoxIds(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(iaBoxIds)) {
                iaBoxIds = new LinkedList<>();
            }
            if(json.isTextual()) {
                iaBoxIds.add(json.asText());
            }
            else {
                for (JsonNode jn : json) {
                    iaBoxIds.add(jn.asText());
                }
            }
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
