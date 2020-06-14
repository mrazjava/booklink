package com.github.mrazjava.booklink.dataimport.openlibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * <a href="https://itnext.io/using-java-to-read-really-really-large-files-a6f8a3f44649">Benchmarks</a>
 */
@Slf4j
@Component
public class CommonsLineIteratorImporter implements FileImporter {

    private ObjectMapper objectMapper;

    private int frequencyCheck = 10000;

    public CommonsLineIteratorImporter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void runImport(File jsonFile, Class schema) {

        String line = null;
        long counter = 0;
        try {
            LineIterator iterator = FileUtils.lineIterator(jsonFile, "UTF-8");

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            while(iterator.hasNext()) {
                line = iterator.next();
                counter++;
                Object pojo = objectMapper.readValue(line, schema);
                if(counter % frequencyCheck == 0) {
                    log.info("JSON #{}:\n{}", counter, objectMapper.writeValueAsString(pojo));
                }
                //log.info("raw JSON #{}:\n{}", counter, line);
                //log.info("raw JSON #{}:\n{}", counter, objectMapper.writeValueAsString(pojo));
            }
            stopWatch.stop();
            log.info("TOTAL RECORDS: {}, time: {}", counter, stopWatch.getTime());
        } catch (IOException e) {
            log.error("JSON #{} failed:\n{}\n\nERROR: {}", counter, line, e.getMessage());
        }
    }

    @Override
    public void setFrequencyCheck(int rowsProcessed) {
        frequencyCheck = rowsProcessed;
    }
}
