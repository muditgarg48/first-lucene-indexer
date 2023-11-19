package Data;

import Constants.QueryConstants;

public class SearchQuery {
    
    private int queryId;
    private String queryText;
    
    public SearchQuery(){
        this.queryId = 0;
        this.queryText = "";
    }

    public int getQueryId(){
        return this.queryId;
    }

    public void setQueryId(int queryId){
        this.queryId = queryId;
    }

    public String getQuery(){
        return this.queryText;
    }

    public void setQuery(String text){
        this.queryText = text;
    }

    public void createQueryObject(String query, int queryId) {
        
        /*
        Since the query Ids need to be sequentiated, 
        the function takes the input of the query id
        instead of deriving it from the query data
        */
        this.setQueryId(queryId);
        String currentDelimiter = QueryConstants.query_delimiter;
        String currentQuery = query.split(currentDelimiter)[1];
        /* 
        Getting the second token which contains the query text 
        and reusing the query memory to store it
        */
        this.setQuery(currentQuery);
    }

    public void displayQuery() {
        System.out.println("Query ID (after sequentiating): "+this.getQueryId());
        System.out.println("Query:\n"+this.getQuery());
    }

}
