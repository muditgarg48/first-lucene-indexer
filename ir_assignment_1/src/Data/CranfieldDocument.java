package Data;

import Constants.DocumentConstants;

public class CranfieldDocument {
    
    private int documentId;
    private String title;
    private String author;
    private String bibliograpy;
    private String body;

    public CranfieldDocument() {
        this.documentId = 0;
        this.title = "";
        this.author = "";
        this.bibliograpy = "";
        this.body = "";
    }

    public CranfieldDocument(
        int documentId, 
        String title, 
        String author, 
        String body, 
        String bibliograpy
    ) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.bibliograpy = bibliograpy;
        this.body = body;
    }

    public int getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBibliography() {
        return this.bibliograpy;
    }

    public void setBibliography(String bibliography) {
        this.bibliograpy = bibliography;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void createDocumentObject(String doc) {
        String delimiters[] = {
            DocumentConstants.title_delimiter,
            DocumentConstants.author_delimiter,
            DocumentConstants.bibliography_delimiter,
            DocumentConstants.body_delimiter
        };

        String currentString = doc;

        for(int i=0;i<delimiters.length;i++) {
            String currentDelimiter = delimiters[i];
            String[] docSections = currentString.split(currentDelimiter);
            switch(i) {
                case 0:
                    // Separating document data by '.T' to get the index of the document
                    int id = Integer.parseInt(docSections[0].trim());
                    this.setDocumentId(id);
                    break;
                case 1:
                    // Separating document data by '.A' to get the title of the document
                    String title = docSections[0].trim();
                    this.setTitle(title);
                    break;
                case 2:
                    // Separating document data by '.B' to get the author of the document
                    String author = docSections[0].trim();
                    this.setAuthor(author);
                    break;
                case 3:
                    // Separating document data by '.W' to get the bibliography and body of the document
                    String bibliography = docSections[0].trim();
                    this.setBibliography(bibliography);
                    String body;
                    //if there is no body of the document
                    if (docSections.length == 1) {
                        body = "";
                    } else {
                        body = docSections[1].trim();
                    }
                    this.setBody(body);
                    return;
                default:
                    System.err.println("Theres something wrong with creating the document object!!");
            }
            // Updating current document data to the rest of the document data
            currentString = docSections[1];
        }
    }

    public void displayDocument() {
        System.out.println("ID: "+this.getDocumentId());
        System.out.println(this.getTitle());
        System.out.println("\t\tBy: "+this.getAuthor());
        System.out.println("\n"+this.getBody()+"\n");
        System.out.println("Bibliography:\n"+this.getBibliography());
    }

}
