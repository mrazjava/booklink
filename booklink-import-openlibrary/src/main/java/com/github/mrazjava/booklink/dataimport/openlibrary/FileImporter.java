package com.github.mrazjava.booklink.dataimport.openlibrary;

import java.io.File;

public interface FileImporter {

    void runImport(File jsonFile, Class schema);
}