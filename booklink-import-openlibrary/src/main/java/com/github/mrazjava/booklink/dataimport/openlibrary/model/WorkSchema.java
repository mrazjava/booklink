package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.mrazjava.booklink.dataimport.openlibrary.model.work.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WorkSchema {

    TypeValue created;

    String title;

    String subtitle;

    List<Long> covers;

    @JsonProperty("last_modified")
    TypeValue lastModified;

    @JsonProperty("latest_revision")
    Integer latestRevision;

    String key;

    List<Author> authors;

    @JsonProperty("subject_places")
    List<String> subjectPlaces;

    @JsonProperty("subject_people")
    List<String> subjectPeople;

    @JsonProperty("subject_times")
    List<String> subjectTimes;

    List<String> subjects;

    @JsonProperty("other_titles")
    List<String> otherTitles;

    List<String> genres;

    @JsonProperty("original_languages")
    List<Key> originalLanguages;

    @JsonProperty("first_publish_date")
    String firstPublishDate;

    @JsonProperty("cover_edition")
    Key coverEdition;

    String description;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    String firstSentence;

    @JsonIgnore
    Object type;

    String ospid;

    TypeValue notes;

    Integer revision;

    List<Link> links;

    @JsonProperty("lc_classifications")
    List<String> classifications;

    @JsonProperty("dewey_number")
    List<String> dweyNumbers;

    List<Excerpt> excerpts;

    String location;

    Key permission;

    @JsonProperty("remote_ids")
    RemoteIds remoteIds;

    @JsonProperty("translated_titles")
    List<TranslatedTitle> translatedTitles;

    @JsonProperty("table_of_contents")
    List<TableOfContent> toc;

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
        if (json != null) {
            String text;
            if (json.isTextual()) {
                text = json.asText();
            } else {
                text = json.get("value").asText();
            }
            firstSentence = text;
        }
    }

    @JsonGetter("first_sentence")
    public String getFirstSentence() {
        return firstSentence;
    }

    @JsonSetter("excerpts")
    public void setJsonExcerpts(JsonNode json) {
        if(json != null) {
            if(CollectionUtils.isEmpty(excerpts)) {
                excerpts = new LinkedList<>();
            }
            if(json.has("")) {
                JsonNode babol = json.get("");
                for(int b = 0; b < babol.size(); b++) {
                    JsonNode jsonExcerpt = babol.get(b);
                    excerpts.add(produceExcerpt(jsonExcerpt));
                }
            }
            else {
                for (int x = 0; x < json.size(); x++) {
                    excerpts.add(produceExcerpt(json.get(x)));
                }
            }
        }
    }

    private Excerpt produceExcerpt(JsonNode json) {

        Excerpt excerpt = new Excerpt();

        if(json.has("excerpt")) {
            excerpt.setExcerpt(json.get("excerpt").asText());
        }
        if(json.has("type")) {
            excerpt.setType(json.get("type").asText());
        }
        if(json.has("value")) {
            excerpt.setValue(json.get("value").asText());
        }
        if(json.has("pages")) {
            excerpt.setPages(json.get("pages").asText());
        }
        if(json.has("page")) {
            excerpt.setPage(json.get("page").asText());
        }
        if(json.has("author")) {
            Key author = new Key();
            author.setKey(json.get("author").asText());
            excerpt.setAuthor(author);
        }
        if(json.has("comment")) {
            excerpt.setComment(json.get("comment").asText());
        }

        return excerpt;
    }
}
