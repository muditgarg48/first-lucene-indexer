import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import Constants.GlobalConstants;
import Data.CranfieldDocument;
import Readers.DataReader;
import Scoring.Similarity;

public class LuceneIndexer {


    public Document convertToLuceneDocument(CranfieldDocument doc) {
        Document document = new Document();
        // Add fields to the Lucene document
        document.add(new StringField("documentId", String.valueOf(doc.getDocumentId()), Field.Store.YES));
        document.add(new TextField("title", doc.getTitle(), Field.Store.YES));
        document.add(new TextField("author", doc.getAuthor(), Field.Store.YES));
        document.add(new TextField("bibliography", doc.getBibliography(), Field.Store.YES));
        document.add(new TextField("body", doc.getBody(), Field.Store.YES));
        return document;
    }

    public void main() {
        
        Path indexFolder = Paths.get(GlobalConstants.INDEX_PATH);
        
        try {

            // Opening the index folder and the index writer to start the process
            Directory indexDirectory = FSDirectory.open(indexFolder);
            IndexWriterConfig config = new IndexWriterConfig(GlobalConstants.getAnalyzer());
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            //Setting similarity method
            Similarity.setIndexWriterSimilarityB25(config);
            IndexWriter indexWriter = new IndexWriter(indexDirectory, config); 
            
            // Getting all the Cranfield documents using the Data Reader class object
            DataReader cr = new DataReader();
            cr.readCollection();
            List<CranfieldDocument> cranfieldCollection = cr.getAllCranDocuments();

            // Converting every cranfield document into Lucene Document and indexing it!
            int cranfieldCollectionSize = cranfieldCollection.size();
            System.out.println("Indexing "+cranfieldCollectionSize+" documents from Cranfield collection!");
            for(int i=0;i<cranfieldCollectionSize;i++) {
                CranfieldDocument cranfieldDocument = cranfieldCollection.get(i);
                Document luceneDocument = this.convertToLuceneDocument(cranfieldDocument);
                try {
                    indexWriter.addDocument(luceneDocument);
                } catch (Exception e) {
                    System.err.println("There was a problem in indexing a Lucene Document! Document details: ");
                    cranfieldDocument.displayDocument();
                    e.printStackTrace();
                    System.exit(0);
                }
            }

            // Closing necessary writers
            indexWriter.close();
            indexDirectory.close();

        } catch(Exception e) {
            System.err.println("Something went wrong during Indexing Initialization! Check error stack!");
            e.printStackTrace();
        }
    }
}
