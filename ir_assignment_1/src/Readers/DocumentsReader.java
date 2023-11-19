package Readers;

import java.util.ArrayList;
import java.util.List;

import Constants.DocumentConstants;
import Data.CranfieldDocument;

public class DocumentsReader {
    
    List<CranfieldDocument> documents;

    DocumentsReader() {
        this.documents = new ArrayList<>();
    }

    public List<CranfieldDocument> getAllDocuments() {
        return this.documents;
    }

    public void addDocument(CranfieldDocument doc) {
        this.documents.add(doc);
    }

    public void storeDocuments(String dataStream) {
        String currentDelimiter = DocumentConstants.document_delimiter;
        String[] documents = dataStream.split(currentDelimiter);
        for (String currentDoc: documents) {
            if(currentDoc == "")
                continue;
            currentDoc = currentDoc.trim();
            CranfieldDocument currentDocument = new CranfieldDocument();
            currentDocument.createDocumentObject(currentDoc);
            this.addDocument(currentDocument);
        }
    }

}
