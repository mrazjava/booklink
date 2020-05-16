package com.github.mrazjava.booklink.data.openlibrary;

import com.github.mrazjava.booklink.data.openlibrary.codegen.Author;
import com.github.mrazjava.booklink.data.openlibrary.codegen.AuthorSchema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @see <a href="https://javadeveloperzone.com/java-8/java-parse-large-json-file-gson-example/">Parse Large JSON file</a>
 */
@Component
public class GsonImporter {

    private static final Logger log = LoggerFactory.getLogger(GsonImporter.class);

    void runImport(File jsonFile) {

        long start = System.currentTimeMillis();

        try (JsonReader jsonReader = new JsonReader(
                new InputStreamReader(
                        new FileInputStream(jsonFile), StandardCharsets.US_ASCII))) {
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray(); //start of json array
            int numberOfRecords = 0;
            while (jsonReader.hasNext()) { //next json array element
                AuthorSchema author = gson.fromJson(jsonReader, AuthorSchema.class);
                //do something real
                log.debug("-------------- RECORD # {} ---------------", ++numberOfRecords);
                log.debug("\n{}", author);
            }
            jsonReader.endArray();
            log.info("Total Records Found : {}", numberOfRecords);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Total Time Taken : {} secs", (System.currentTimeMillis() - start)/1000);
    }
}
