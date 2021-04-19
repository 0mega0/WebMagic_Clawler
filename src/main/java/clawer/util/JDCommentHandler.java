package clawer.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Date 2021/4/19
 * @author 0mega_0
 * JD Comment page https://club.jd.com/comment/productPageComments.action?&productId= + 100008348530(Item ID) + &score=0&sortType=5&page=0&pageSize=1
 * 因为东西太多了，就没单独建JavaBean了，用字符串提取来实现
 */
public class JDCommentHandler {
    private static final Logger log = Logger.getLogger(JDCommentHandler.class);
    private URL targetUrl;
    private String commentRate;
    private String commentCount;



    public JDCommentHandler(String targetID) {
        String front="https://club.jd.com/comment/productPageComments.action?&productId=";
        String end = "&score=0&sortType=5&page=0&pageSize=1";
        try{
            this.targetUrl = new URL(front + targetID + end);
        } catch(IOException e){
            log.error(e.toString() + "on getting comment [" + targetID + "]");
        }

        process(targetUrl);
    }
    public void process(URL targetUrl){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(targetUrl.openStream(),"gb2312"));
            String js="",inputLine;
            while ((inputLine = in.readLine()) != null)
                js+=inputLine;
            in.close();
            String[] jss = js.split("}");

            int a = jss[0].indexOf("commentCountStr");
            int b = jss[0].indexOf(",",a);
            String[] commentCountStr = jss[0].substring(a,b).split("\"");
            this.commentCount = commentCountStr[2];

            int c = jss[0].indexOf("goodRate");
            int d = jss[0].indexOf(",",c);
            String[] commentRateStr = jss[0].substring(c,d).split(":");
            this.commentRate = commentRateStr[1];
        } catch(IOException e){
            log.error(e.toString() + "on getting comment [" + targetUrl + "]");
        }catch(StringIndexOutOfBoundsException e){
            log.error(e.toString() + "on getting comment [" + targetUrl + "]");
        }

    }
    public String getCommentRate() {
        return commentRate;
    }

    public String getCommentCount() {
        return commentCount;
    }
}
