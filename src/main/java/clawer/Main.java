package clawer;

import clawer.pageProcessors.JDPage;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String args[]){
        startClawingJD();
    }

    private static void startClawingJD(){
        log.info("启动爬取京东商品的爬虫");
        String startPage = "https://list.jd.com/list.html?cat=9987,653,655&page=";
        int pageNumber = 10;
        //爬取京东商品页面的前 pageNumber 页
        Spider spider = Spider.create(new JDPage());
        //test(spider);
        normal(spider,startPage,pageNumber);
        spider.addPipeline(new JsonFilePipeline("D:\\data collection\\webmagic"));
        spider.thread(3).run();

    }

    private static void test(Spider spider){
        log.info("test method run");
        String testPage = "https://item.jd.com/100008348530.html";
        String testPage2 = "https://item.jd.com/100014348492.html";
        spider.addUrl(testPage);
        spider.addUrl(testPage2);
    }
    private static void normal(Spider spider,String startPage,int pageNumber){
        for(int i=1; i<=pageNumber; i++){
            spider.addUrl(startPage+i);
        }
    }
}
