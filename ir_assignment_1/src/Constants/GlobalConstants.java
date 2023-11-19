package Constants;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
// import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class GlobalConstants {
    public final static String INDEX_LOCATION = "";
    public final static String CRAN_COLLECTION_FILE_NAME = "cran.all.1400";
    public final static String CRAN_QUERIES_FILE_NAME = "cran.qry";
    public final static String RELEVANCE_JUDGEMENT_FILE_NAME = "cranqrel";
    public final static String RESULT_FILE = "results.txt";

    public final static String CRAN_COLLECTION_FILES_PATH = "../cran/";
    public final static String INDEX_PATH = "../index/";

    public final static String DOCUMENT_COLLECTION_FILE = 
        CRAN_COLLECTION_FILES_PATH + CRAN_COLLECTION_FILE_NAME;
    public final static String QUERY_COLLECTION_FILE = 
        CRAN_COLLECTION_FILES_PATH + CRAN_QUERIES_FILE_NAME;
    public final static String RESULT_FILE_PATH = 
        CRAN_COLLECTION_FILES_PATH + RESULT_FILE;

    public final static int maxNumberOfResults = 50;

    public static Analyzer getAnalyzer() {
        Analyzer ourAnalyzer = new EnglishAnalyzer();
        // Analyzer ourAnalyzer = new StandardAnalyzer();
        return ourAnalyzer;
    }

}