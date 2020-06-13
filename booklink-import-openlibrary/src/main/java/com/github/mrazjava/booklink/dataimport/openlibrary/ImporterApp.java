package com.github.mrazjava.booklink.dataimport.openlibrary;

import com.github.mrazjava.booklink.dataimport.openlibrary.model.work.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ImporterApp implements ApplicationRunner {

	@Autowired @Qualifier("gson")
	private FileImporter gsonImporter;

	@Autowired @Qualifier("commons")
	private FileImporter commonsImporter;



	public static void main(String[] args) {

		SpringApplication.run(ImporterApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		//runGsonImporter("authors-head-n20.json", AuthorSchema.class);
		runCommonsImporter("works-head-n1000.json", Work.class);
		//runCommonsImporter("authors-head-n20-1.json", AuthorSchema.class);
	}

	private void runGsonImporter(String fileName, Class schema) {
		gsonImporter.runImport(
				new File(getClass().getResource(String.format("/openlibrary/samples/%s", fileName)).getFile()),
				schema
		);
	}

	private void runCommonsImporter(String fileName, Class schema) {

		commonsImporter.runImport(
				new File(getClass().getResource(String.format("/openlibrary/samples/%s", fileName)).getFile()),
				schema
		);
	}
}
