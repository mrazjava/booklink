package com.github.mrazjava.booklink.dataimport.openlibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class OpenLibraryDataApp {

	public static void main(String[] args) {

		SpringApplication.run(OpenLibraryDataApp.class, args);
	}
}
