package clawer.lucene;

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

import static org.junit.jupiter.api.Assertions.*;

import org.apache.lucene.util.BytesRef;

class LuceneQueryTest {
    private static final Logger log = Logger.getLogger(LuceneQueryTest.class);
    private String dirPath;
    private static IndexReader reader;
    private static IndexSearcher searcher;
    private static Analyzer analyzer = new HanLPAnalyzer();
    public static void main(String[] arg){
        LuceneQueryTest lqt = new LuceneQueryTest("D:\\data collection\\webmagic\\lucene");
        //Query query = new TermRangeQuery("PRICE",new BytesRef("0"),new BytesRef("10000"),true,true);
        //excQuery(query);
        try {
            //lqt.searchByPriceRange(0,20000);
            lqt.booleanQuery("三星","TITLE:120hz");
//            Query query = new TermQuery(new Term("TITLE","三星"));
//            excQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public LuceneQueryTest(String path){
        try{
            this.dirPath = path;
            File file = new File(dirPath);
            Directory dir = FSDirectory.open(file.toPath());
            reader = DirectoryReader.open(dir);
            searcher = new IndexSearcher(reader);
        }catch (IOException e){
            log.error("[Exception on reading index file ]" + e);
        }
    }
    public static void excQuery(Query query){
        //查询
        try {
            log.info("在查了");

            TopDocs topDocs = searcher.search(query, 100);
            System.out.println("检索到结果数: "+topDocs.totalHits);
            for(ScoreDoc scoreDoc : topDocs.scoreDocs){
                //湖区偶
                Document doc = reader.document(scoreDoc.doc);
                System.out.println(doc.get("ID")+":"+doc.get("TITLE"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void searchByPriceRange(double low, double high) throws IOException {
        Query query = DoublePoint.newRangeQuery("PRICE", low, high);
        TopDocs results = searcher.search(query, 100);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = results.totalHits;
        System.out.println("一共查询到[" + numTotalHits + "]条信息");
        int number = Math.min(hits.length, 100);
        for (int i = 0; i < number; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String name = doc.get("TITLE");
            String url = doc.get("URL");
            System.out.println(url +"\n"+ name+"\n");
            // System.out.println("doc=" + hits[i].doc + " score=" +
            // hits[i].score);
        }
    }
    public void booleanQuery(String name,String parameter) throws ParseException, IOException{
        BooleanQuery.Builder booleanBuilder = new BooleanQuery.Builder();
        // 手机名进行查询
        TermQuery nameQuery = new TermQuery(new Term("TITLE", name));
        // 对参数进行查询
        QueryParser parameterParser = new QueryParser("PARAMETER", analyzer);	//初始化分词器
        Query parameterQuery = parameterParser.parse(parameter);
        // 合并
        booleanBuilder.add(nameQuery, BooleanClause.Occur.MUST);
        booleanBuilder.add(parameterQuery, BooleanClause.Occur.MUST);
        BooleanQuery bq = booleanBuilder.build();
        // 进行查询
        TopDocs results = searcher.search(bq,100);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = results.totalHits;
        System.out.println("一共查询到【" + numTotalHits + "】部手机符合要求：");
        int number = Math.min(hits.length, 100);
        for (int i = 0; i < number; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String cellphoneName = doc.get("TITLE");
            String url = doc.get("URL");
            System.out.println(url + "  " + cellphoneName);
            // System.out.println("doc=" + hits[i].doc + " score=" +
            // hits[i].score);
        }
    }
}