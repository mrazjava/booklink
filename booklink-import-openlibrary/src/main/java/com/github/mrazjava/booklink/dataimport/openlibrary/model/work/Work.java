package com.github.mrazjava.booklink.dataimport.openlibrary.model.work;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Work {

    TypeValue created;

    String title;

    String subtitle;

    List<Long> covers;

    @JsonProperty("last_modified")
    TypeValue lastModified;

    @JsonProperty("latest_revision")
    Integer latestRevision;

    String key;

    List<TypeAuthor> authors;

    @JsonProperty("subject_places")
    List<String> subjectPlaces;

    @JsonProperty("subject_people")
    List<String> subjectPeople;

    @JsonProperty("subject_times")
    List<String> subjectTimes;

    List<String> subjects;

    @JsonProperty("cover_edition")
    Key coverEdition;

    TypeValue description;

    @JsonIgnore
    Object type;

    Integer revision;
}
