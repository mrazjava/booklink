package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TableOfContent {

    @JsonIgnore
    Key type;

    String pagenum;

    String title;

    String label;

    @JsonProperty("class")
    String clazz;

    @JsonSetter("value")
    void setValue(String value) {
        title = value;
    }
}
