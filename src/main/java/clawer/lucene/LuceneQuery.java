package clawer.lucene;

/**
 * Date 2021/5/19
 * @author 0mega_0
 */

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class LuceneQuery {
    private static final Logger log = Logger.getLogger(LuceneQuery.class);
    private String dirPath;
    private static IndexReader reader;
    private static IndexSearcher searcher;

    public LuceneQuery(String path) {
        try {
            this.dirPath = path;
            File file = new File(dirPath);
            Directory dir = FSDirectory.open(file.toPath());
            reader = DirectoryReader.open(dir);
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            log.error("[Exception on reading index file ]" + e);
        }
    }

    public int getDocCount() {
        try {
            return reader.getDocCount("ID");
        } catch (IOException e) {
            log.error("[Exception on LuceneQuery ]" + e);
        }
        return -1;
    }

    public void searchByTitle(String context) {
        Query query = new TermQuery(new Term("TITLE", context));
        execQuery(query);
    }

    public void searchByPriceRange(double lowRange, double upRange) {
        Query query = DoublePoint.newRangeQuery("PRICE", lowRange, upRange);
        execQuery(query);
    }

    public void booleanQuery(String context, String parameter) throws ParseException {
        /*
         * context:输入的关键词
         * parameter:输入的参数，如TITLE:120HZ
         */
        Analyzer analyzer = new HanLPAnalyzer();
        BooleanQuery.Builder bQBuilder = new BooleanQuery.Builder();
        TermQuery query_Title = new TermQuery(new Term("TITLE", context));
        QueryParser parser = new QueryParser("PARAMETER", analyzer);
        Query query_parser = parser.parse(parameter);
        bQBuilder.add(query_parser, BooleanClause.Occur.MUST).add(query_Title, BooleanClause.Occur.MUST);
        BooleanQuery booleanQuery = bQBuilder.build();
        execQuery(booleanQuery);
    }

    public void execQuery(Query query) {
        try {
            log.info("执行查询操作");
            TopDocs topDocs = searcher.search(query, 100);
            System.out.println("检索到结果数: " + topDocs.totalHits);
            printQueryResult(topDocs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printQueryResult(TopDocs topDocs) {
        for (ScoreDoc sd : topDocs.scoreDocs) {
            try {
                Document doc = reader.document(sd.doc);
                System.out.println(doc.get("TITLE") + "\n\t" + doc.get("URL"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void closeReader(){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
