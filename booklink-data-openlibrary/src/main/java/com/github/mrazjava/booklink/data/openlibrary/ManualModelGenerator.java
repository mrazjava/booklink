package com.github.mrazjava.booklink.data.openlibrary;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Experimentation. Use mvn generate-sources, see readme.
 */
@Component
public class ManualModelGenerator {

    private static final Logger log = LoggerFactory.getLogger(ManualModelGenerator.class);

    void generateModels(String schemaFile) throws IOException {
        JCodeModel codeModel = new JCodeModel();
        URL source = new File(schemaFile).toURI().toURL(); //DataProcessor.class.getResource(schemaFile);
        log.info("source: {}", source);

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, "ClassName", "com.github.mrazjava.booklink.data.openlibrary.model", source);

        codeModel.build(new File("src/main/java/"));
    }
}
