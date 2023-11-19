package Readers;

import java.util.ArrayList;
import java.util.List;

import Constants.QueryConstants;
import Data.SearchQuery;

public class QueryReader {
    
    List<SearchQuery> queries;

    QueryReader() {
        this.queries = new ArrayList<>();
    }

    public List<SearchQuery> getAllQueries() {
        return this.queries;
    }

    public void addQuery(SearchQuery q) {
        this.queries.add(q);
    }

    public void storeQueries(String dataStream) {
        String currentDelimiter = QueryConstants.queries_delimiter;
        // A counter variable to get the sequency number of the query to be used as the Query ID
        int queryId = 1;
        // Get all the sections of the string separated by .I to get all document data
        String queries[] = dataStream.split(currentDelimiter);
        for(String currentQ: queries) {
            if(currentQ == "")
                continue;
            currentQ = currentQ.trim();
            SearchQuery currentQuery = new SearchQuery();
            currentQuery.createQueryObject(currentQ, queryId);
            this.addQuery(currentQuery);
            queryId++;
        }
        queryId--;
        System.out.println(queryId+" number of queries stored in memory !!");
    }

}
