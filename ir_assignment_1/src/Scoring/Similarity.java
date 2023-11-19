package Scoring;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;

public class Similarity {

    public static void setIndexSearcherSimilarityVSM(IndexSearcher indexSearcher) {
        indexSearcher.setSimilarity(new ClassicSimilarity());
    }
    
    public static void setIndexWriterSimilarityVSM(IndexWriterConfig indexWriter) {
        indexWriter.setSimilarity(new ClassicSimilarity());
    }

    public static void setIndexSearcherSimilarityB25(IndexSearcher indexSearcher) {
        indexSearcher.setSimilarity(new BM25Similarity());
    }

    public static void setIndexWriterSimilarityB25(IndexWriterConfig indexWriter) {
        indexWriter.setSimilarity(new BM25Similarity());
    }

}
