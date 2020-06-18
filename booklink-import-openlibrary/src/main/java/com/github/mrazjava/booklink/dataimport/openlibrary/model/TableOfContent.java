package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
public class TableOfContent {

    @JsonIgnore
    Key type;

    String pagenum;

    String title;

    String label;

    @JsonProperty("class")
    String clazz;

    Integer level;

    @JsonSetter("value")
    void setValue(String value) {
        title = value;
    }


    public TableOfContent(String value) {
        this.title = value;
    }
}
