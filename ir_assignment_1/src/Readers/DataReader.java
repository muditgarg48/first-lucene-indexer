package Readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Constants.GlobalConstants;
import Data.CranfieldDocument;
import Data.SearchQuery;

public class DataReader {
    
    List<CranfieldDocument> documentCollection;
    List<SearchQuery> queryCollection;

    public DataReader() {
        this.documentCollection = new ArrayList<>();
        this.queryCollection = new ArrayList<>();
    }

    public List<CranfieldDocument> getAllCranDocuments() {
        return this.documentCollection;
    }

    public List<SearchQuery> getAllQueries() {
        return this.queryCollection;
    }

    public void readCollection() {
        String path = GlobalConstants.DOCUMENT_COLLECTION_FILE;
        Path collectionPath = Paths.get(path);
        String content;
        try {
            // Read the entire content of the collections file into a string
            content = Files.readString(collectionPath);
            DocumentsReader dr = new DocumentsReader();
            dr.storeDocuments(content);
            this.documentCollection = dr.getAllDocuments(); 
        } catch (FileNotFoundException e) {
            System.err.println("Document collection file not found at "+path+"!");
            e.printStackTrace();
        } catch(IOException e) {
            System.err.println("IO Exception thrown. Error: "+e+"\n");
            e.printStackTrace();
        } catch(Exception e) {
            System.err.println("Something unrecognised went wrong. Error: "+e+"\n");
            e.printStackTrace();
        }
    }

    public void readQueries() {
        String path = GlobalConstants.QUERY_COLLECTION_FILE;
        Path collectionPath = Paths.get(path);
        String content;
        try {
            // Read the entire content of the collections file into a string
            content = Files.readString(collectionPath);
            QueryReader q = new QueryReader();
            q.storeQueries(content);
            this.queryCollection = q.getAllQueries(); 
        } catch (FileNotFoundException e) {
            System.err.println("Query collection file not found at "+path+"!");
            e.printStackTrace();
        } catch(IOException e) {
            System.err.println("IO Exception thrown. Error: "+e+"\n");
            e.printStackTrace();
        } catch(Exception e) {
            System.err.println("Something unrecognised went wrong. Error: "+e+"\n");
            e.printStackTrace();
        }
    }

}
