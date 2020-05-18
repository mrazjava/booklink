package com.github.mrazjava.booklink.data.openlibrary;

import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ImporterApp implements ApplicationRunner {

	@Autowired
	private GsonImporter gsonImporter;


	public static void main(String[] args) {

		SpringApplication.run(ImporterApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		gsonImporter.runImport(new File(getClass().getResource("/openlibrary/samples/authors-head-n20.json").getFile()));
	}
}
