package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@UserDefinedType("bookweight")
@Data
public class Weight {

    private String unit;

    private String weight;
}
