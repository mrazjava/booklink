package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Weight {

    private String unit;

    private String weight;
}
