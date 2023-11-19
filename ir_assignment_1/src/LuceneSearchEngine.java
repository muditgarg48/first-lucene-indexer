public class LuceneSearchEngine {
    
    public static void main(String[] args) {

        LuceneIndexer li = new LuceneIndexer();
        QuerySearcher qi = new QuerySearcher();

        li.main();

        System.out.println("The system has finished indexing !!");

        qi.main();
    }


}
