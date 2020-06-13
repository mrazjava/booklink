package com.github.mrazjava.booklink.dataimport.openlibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * Requires file content as a valid JSON array.
 *
 * @see <a href="https://javadeveloperzone.com/java-8/java-parse-large-json-file-gson-example/">Parse Large JSON file</a>
 */
@Slf4j
@Component @Qualifier("gson")
public class GsonImporter implements FileImporter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void runImport(File jsonFile, Class schema) {

        long start = System.currentTimeMillis();

        try (JsonReader jsonReader = new JsonReader(
                new InputStreamReader(
                        new FileInputStream(jsonFile), StandardCharsets.US_ASCII))) {
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray(); //start of json array
            int numberOfRecords = 0;
            while (jsonReader.hasNext()) { //next json array element
                Object pojo = gson.fromJson(jsonReader, schema);
                //do something real
                log.debug("-------------- RECORD # {} ---------------", ++numberOfRecords);
                log.debug("{}\n{}", schema, pojo);
                log.info("Author JSON:\n{}", objectMapper.writeValueAsString(pojo));

            }
            jsonReader.endArray();
            log.info("Total Records Found : {}", numberOfRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Total Time Taken : {} secs", (System.currentTimeMillis() - start)/1000);
    }
}
