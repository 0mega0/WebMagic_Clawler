package clawer.pipeline;

/**
 * Date 2021/5/13
 * @author 0mega_0
 */

import clawer.lucene.Lucene;
import clawer.model.Phone;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyLucenePipeline implements Pipeline {

    private static final Logger log = Logger.getLogger(MyLucenePipeline.class);
    public MyLucenePipeline() { }

    public void process(ResultItems resultItems, Task task) {
        //实现对抓取出来的结果集的提取和索引工作
        Phone phone = resultItems.get("PHONES_INFO");
        Lucene.getInstance().addPhoneIndex(phone);
    }

}
