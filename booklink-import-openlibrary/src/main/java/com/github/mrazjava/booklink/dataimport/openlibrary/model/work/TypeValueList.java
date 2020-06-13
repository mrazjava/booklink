package com.github.mrazjava.booklink.dataimport.openlibrary.model.work;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 */
@Data
public class TypeValueList {

    String type;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<String> value;
}
