package com.github.mrazjava.booklink.dataimport.openlibrary.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Identifiers {

    @JsonProperty("barcode")
    private List<String> barCode;

    private List<String> oclc;

    private List<String> goodreads;

    private List<String> librarything;

    @JsonAlias({
            "library_of_congress",
            "library_of_congress_classification_(lcc)",
            "library_of_congress_catalogue_number"})
    private List<String> lccn;

    private List<String> wikidata;

    @JsonProperty("call_number")
    private List<String> callNumber;

    @JsonProperty("media_mint_publishing")
    private List<String> mediaMintPublishing;

    @JsonProperty("judaica_savings")
    private List<String> judaicaSavings;

    private List<String> freebase;

    @JsonProperty("dewey_decimal_classification_(ddc)")
    private List<String> deweyDecimalClass;

    @JsonProperty("create_space")
    private List<String> createSpace;

    @JsonAlias("smashwords.com")
    @JsonProperty("smashwords_book_download")
    private List<String> smashwordsBookDownload;

    @JsonProperty("imslp/petrucci_music_library")
    private List<String> petrucciMusicLibrary;

    @JsonProperty("national_diet_library,_japan")
    private List<String> nationalDietLibraryJapan;

    @JsonProperty("british_national_bibliography")
    private List<String> britishNationalBibliography;

    @JsonProperty("british_library")
    private List<String> britishLibrary;

    @JsonProperty("bayerische_staatsbibliothek")
    private List<String> bayerischeBibliothek;

    @JsonProperty("deutsche_nationalbibliothek_(urn:nbn)")
    private List<String> deutscheNationalBibliothek;

    @JsonProperty("staats-_und_universitätsbibliothek_hamburg")
    private List<String> universitatBibliothekHamburg;

    @JsonAlias("bibliothèque_nationale_de_france_(bnf)")
    @JsonProperty("bibliothèque_nationale_de_france")
    private List<String> bibliothequeNationaleDeFrance;

    @JsonAlias("depósito_legal")
    @JsonProperty("depósito_legal_bolivia")
    private List<String> depositoLegalBolivia;

    @JsonProperty("cornell_university_online_library")
    private List<String> cornellUniversityOnlineLibrary;

    @JsonProperty("boston_public_library")
    private List<String> bostonPublicLibrary;

    @JsonProperty("abebooks,de")
    private List<String> abeBooksDe;

    @JsonProperty("usaid/dec")
    private List<String> usAidDec;

    @JsonProperty("kindle.com")
    private List<String> kindle;

    private List<String> bookwire;

    @JsonProperty("bol.com")
    private List<String> bol;

    private List<String> bibsys;

    @JsonProperty("abwa_bibliographic_number")
    private List<String> abwaBibliographicNumber;

    @JsonProperty("magcloud")
    private List<String> magCloud;

    private List<String> flipkart;

    private List<String> upc;

    @JsonProperty("taideteollisen_korkeakoulun_julkaisusarja")
    private List<String> taideteollisenKorkeakolunJulkaisusarija;

    @JsonProperty("bestell-nr._(ddr)")
    private List<String> bestellNr;

    @JsonProperty("canadian_national_library_archive")
    private List<String> canadianNationalLibraryArchive;

    @JsonProperty("dominican_institute_for_oriental_studies_library")
    private List<String> dominicanInstituteForOrientalStudiesLibrary;

    @JsonProperty("paperback_swap")
    private List<String> paperbackSwap;

    @JsonProperty("newberry_library_cartographic_catalog")
    private List<String> newberryLibraryCartographicCatalog;

    @JsonProperty("bookmooch")
    private List<String> bookMooch;

    @JsonProperty("booksforyou")
    private List<String> booksForYou;

    private List<String> lulu;

    private List<String> amazon;

    @JsonProperty("amazon.de_asin")
    private List<String> amazonDe;

    @JsonProperty("amazon.co.uk_asin")
    private List<String> amazonUk;

    @JsonProperty("amazon.ca_asin")
    private List<String> amazonCa;

    @JsonProperty("amazon.it_asin")
    private List<String> amazonIt;

    private List<String> google;

    private List<String> dnb;

    private List<String> nla;

    private List<String> issn;

    private List<String> isbn;

    @JsonAlias("harvard_university_library")
    private List<String> harvard;

    @JsonProperty("bodleian,_oxford_university")
    private List<String> oxford;

    private List<String> overdrive;

    private List<String> shelfari;

    private List<String> ean;

    private List<String> bhl;

    private List<String> format;

    @JsonProperty("choosebooks")
    private List<String> choseBooks;

    @JsonProperty("project_gutenberg")
    private List<String> projectGutenberg;

    @JsonProperty("hathi_trust")
    private List<String> hathiTrust;

    @JsonProperty("alibris_id")
    private List<String> alibrisId;

    private List<String> bcid;

    @JsonProperty("zdb-id")
    private List<String> zdbId;

    private List<String> isfdb;

    private List<String> libris;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Key> works;

    private List<String> scribd;

    private List<String> ulrls;

    @JsonProperty("link")
    private List<String> links;

    @JsonProperty("open_library")
    private List<String> openLibrary;
}
