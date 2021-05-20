package clawer.pipeline;
/**
 * Date 2021/5/11
 * @author 0mega_0
 * MyJsonPipeline: output result files as json files.
 */

import com.alibaba.fastjson.JSON;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class MyJsonPipeline extends FilePersistentBase implements Pipeline {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public MyJsonPipeline() {
        this.setPath("/data/webmagic");
    }

    public MyJsonPipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        try {
            //PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".json")));
            String url = resultItems.getRequest().getUrl();
            if(url.startsWith("https://ccc-x.jd.com/")){
                log.info("广告页面，跳过处理 ["+url+"]");
                return;
            }
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(path + url.substring(20,url.length()-5) +  ".json")));
            printWriter.write(JSON.toJSONString(resultItems.getAll()));
            printWriter.close();
        } catch (IOException var5) {
            this.log.warn("write file error", var5);
        }

    }


}
