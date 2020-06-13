package com.github.mrazjava.booklink.dataimport.openlibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * <a href="https://itnext.io/using-java-to-read-really-really-large-files-a6f8a3f44649">Benchmarks</a>
 */
@Slf4j
@Component("commons")
public class CommonsLineIteratorImporter implements FileImporter {

    private ObjectMapper objectMapper;

    public CommonsLineIteratorImporter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void runImport(File jsonFile, Class schema) {

        try {
            LineIterator iterator = FileUtils.lineIterator(jsonFile, "UTF-8");
            int count = 0;
            while(iterator.hasNext()) {
                String line = iterator.next();
                Object pojo = objectMapper.readValue(line, schema);
                log.info("JSON #{}:\n{}", ++count, objectMapper.writeValueAsString(pojo));
                //log.info("raw JSON #{}:\n{}", ++count, line);
            }
            log.info("TOTAL RECORDS: {}", count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
