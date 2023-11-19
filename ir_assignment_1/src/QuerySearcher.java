
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.BufferedWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import Constants.GlobalConstants;
import Data.CranfieldDocument;
import Data.SearchQuery;
import Readers.DataReader;
import Scoring.Similarity;

public class QuerySearcher {

    public void printSearchResultAndScore(Document searchResult, float resultScore) {
        int docId = Integer.parseInt(searchResult.get("documentId"));
        String title = searchResult.get("title");
        String author = searchResult.get("author");
        String body = searchResult.get("body");
        String bibliography = searchResult.get("bibliography");
        CranfieldDocument doc = new CranfieldDocument(docId, title, author, body, bibliography);
        // doc.displayDocument();
    }

    public void writeToResult(
        BufferedWriter resultWriter, 
        int queryId, 
        float resultScore, 
        int documentId,
        int rankNumber
        ) {
        String score = String.format("%.6f", resultScore);
        String queryID = String.format("%3d", queryId);
        String documentID = String.format("%4d", documentId);
        String rank = String.format("%2d", rankNumber);
        String queryResultLine = (
            queryID + "\tQ0\t" + documentID + "\t" + rank + "\t" + score + "\tSTANDARD"
        );
        try {
            resultWriter.write(queryResultLine);
            resultWriter.newLine();
            System.out.println("Result for query number: "+queryID+" and result rank: "+rank+" written to the txt");
        } catch (Exception e) {
            System.err.println("Theres a problem writing the search query request results into the results file");
        }
    }

    public void main() {
        
		try {

            Analyzer analyzer = GlobalConstants.getAnalyzer();
            Path indexFolder = Paths.get(GlobalConstants.INDEX_PATH);

            // Opening the index folder and the index reader to start the search
            Directory directory = FSDirectory.open(indexFolder);
            DirectoryReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //Setting similarity method
            Similarity.setIndexSearcherSimilarityB25(indexSearcher);

            // Getting Writers ready to write to the results file for TREC_EVAL
            FileWriter fw = new FileWriter(GlobalConstants.RESULT_FILE_PATH, false);
		    BufferedWriter resultWriter = new BufferedWriter(fw);

            // Retrieving all the queries from the Data Reader class
            DataReader cr = new DataReader();
            cr.readQueries();
            List<SearchQuery> queryList = cr.getAllQueries();

            // Fields of importance are author (weightage of 10%), title (weightage of 20%) and body (weightage of 70%)
            String searchFields[] = {"author", "title", "body"};
            Map<String, Float> fieldImportance = new HashMap<>();
            fieldImportance.put("author", 0.10f);
            fieldImportance.put("title", 0.20f);
            fieldImportance.put("body", 0.70f);
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(searchFields, analyzer, fieldImportance);

            int resultCount = GlobalConstants.maxNumberOfResults;

            for(int i=0; i < queryList.size(); i++)
            {

                String queryText = queryList.get(i).getQuery();

                if (queryText.length() > 0)
                {
                    Query query = null;
                    try {
                        query = queryParser.parse(queryText);
                    } catch (Exception e) {
                        System.err.println(e+". Skipping query!");
                        continue;
                    }
                    TopDocs topResults = indexSearcher.search(query, resultCount);
                    int queryId = i+1;
                    int rankNumber = 1;
                    for (ScoreDoc scoreDocument: topResults.scoreDocs) {
                        int documentId = scoreDocument.doc;
                        // Document searchResult = indexSearcher.doc(documentId);
                        float resultScore = scoreDocument.score;
                        System.out.println("QueryID: "+queryId+" Score: "+resultScore);
                        // printSearchResultAndScore(searchResult, resultScore);
                        writeToResult(resultWriter, queryId, resultScore, documentId, rankNumber++);
                    }
                }
            }
            resultWriter.close();
            indexReader.close();
            directory.close();

        } catch (Exception e) {
            System.err.println("Something went wrong while searching the query in the index");
            e.printStackTrace();
        }
	}
}
