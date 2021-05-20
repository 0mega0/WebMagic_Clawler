package clawer.lucene;

/**
 * Date 2021/5/18
 * @author 0mega_0
 */

import clawer.model.Phone;
import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lucene {

    private static final Logger log = Logger.getLogger(Lucene.class);
    private String dirPath;
    private IndexWriter writer;

    private static class ClassInstance{
        private static Lucene LuceneInstance = new Lucene();
    }
    private Lucene(){ }
    public static Lucene getInstance(){
        return ClassInstance.LuceneInstance;
    }

    public void init(String path){//初始化
        try {
            this.dirPath = path;
            File file = new File(this.dirPath);
            Directory index = FSDirectory.open(file.toPath());//索引文件
            Analyzer analyzer = new HanLPAnalyzer();//汉语分词analyzer
            IndexWriterConfig IWConfig = new IndexWriterConfig(analyzer);//索引配置config

            this.writer = new IndexWriter(index, IWConfig);
        } catch(Exception e){
            log.error("[Exception on init Lucene] "+e);
        }
    }

    public void addPhoneIndex(Phone phone){
        Document doc = new Document();
        doc.add(new StringField("ID",phone.getId(),Field.Store.YES));
        doc.add(new StoredField("URL",phone.getUrl()));
        doc.add(new StoredField("IMAGE_URL",phone.getImgUrl()));
        doc.add(new TextField("TITLE",phone.getTitle(),Field.Store.YES));
        doc.add(new DoublePoint("PRICE",Double.parseDouble(phone.getPrice())));//double
        doc.add(new StoredField("COMMENT_COUNT",phone.getComment_count()));//String
        doc.add(new DoublePoint("COMMENT_RATE",Double.parseDouble(phone.getComment_rate())));//double
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        doc.add(new StoredField("UPDATE_TIME",formatter.format(date)));
        try {
            writer.updateDocument(new Term("ID",phone.getId()),doc);
            writer.commit();
        } catch (IOException e) {
            log.error("[Exception on update document] " + e);
        }

    }

    public void IndexProcess(){
        int count = 0;
    }

    public IndexWriter getWriter() {
        return writer;
    }
}
