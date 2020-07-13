package com.github.mrazjava.booklink.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Dimensions {

    private String units;

    private String width;

    private String depth;

    private String height;
}
