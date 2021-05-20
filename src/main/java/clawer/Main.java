package clawer;

import clawer.lucene.Lucene;
import clawer.lucene.LuceneQuery;
import clawer.pageProcessors.JDPage;
import clawer.pipeline.MyJsonPipeline;
import org.apache.log4j.Logger;
import clawer.pipeline.MyLucenePipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String args[]) throws Exception {
        //startClawingJD();
        startLuceneQuerying();
    }

    private static void startClawingJD() {//爬虫主方法
        log.info("启动爬取京东商品的爬虫");
        String startPage = "https://list.jd.com/list.html?cat=9987,653,655&page=";//爬虫起始页面
        int pageNumber = 10;//爬取京东商品页面的前 pageNumber 页
        String lucenePath = "D:\\data collection\\webmagic\\lucene";
        Spider spider = Spider.create(new JDPage());

        //test(spider);
        //process_Normal(spider, startPage, pageNumber);
        proccess_Lucene(spider,startPage,pageNumber,lucenePath);



        //spider.addPipeline(new MyJsonPipeline("D:\\data collection\\webmagic"));

        //爬虫的线程数
        spider.thread(3).run();
        try {
            Lucene.getInstance().getWriter().close();//关闭Lucene的FileStream
        } catch (IOException e) {
            log.error("[Exception on closing writer] " + e);
        }


    }

    private static void startLuceneQuerying() throws Exception {//索引检索主方法
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        Boolean isValidState = true;
        log.info("开始索引查询");
        LuceneQuery lq = new LuceneQuery("D:\\data collection\\webmagic\\lucene");
        while (isValidState) {
            System.out.println("选择查询方式(输入数字)\n" +
                    "1:索引文档数\n" +
                    "2:关键词查询\n" +
                    "3:价格区间查询\n" +
                    "4:bool查询\n" +
                    "5:退出\n");
            String input = scanner.next();
            switch (input) {
                case "1":
                    System.out.println("当前索引总数为:" + lq.getDocCount() + "条\n");
                    pressAnyKeyToContinue();
                    break;
                case "2":
                    System.out.println("输入关键词");
                    lq.searchByTitle(in.readLine());
                    pressAnyKeyToContinue();
                    break;
                case "3":
                    System.out.println("输入价格区间(最低价格 最高价格)");
                    lq.searchByPriceRange(scanner.nextDouble(), scanner.nextDouble());
                    pressAnyKeyToContinue();
                    break;
                case "4":
                    System.out.println("输入关键词和参数字段");
                    lq.booleanQuery("三星", "TITLE:120HZ");
                    pressAnyKeyToContinue();
                    break;
                case "5":
                    lq.closeReader();
                    isValidState = false;
                    break;
                default:
                    System.out.println("输入正确的序号");
                    break;

            }

        }
        in.close();
        scanner.close();

    }


    private static void test(Spider spider) {//测试方法
        log.info("test method run");
        String testPage = "https://item.jd.com/100008348530.html";
        String testPage2 = "https://item.jd.com/100014348492.html";
        String testAdvPage = "https://ccc-x.jd.com/dsp/nc?ext=aHR0cDovL2l0ZW0uamQuY29tLzI2NTg1NDYxMzQwLmh0bWw&log=Q52gQr_QMvMnr-gbtg5tQ8YhOL5PJ-iU6KAaHB9XBQ7yqxJCeIDHr1Pye7YZlbcvNtOkGJBLGtLPD_Tfxp_bCOn8xgNVqtevzKlHSOUc4eLxR5dBFj1yDaFWhNGZQ_He7MxR5rmqM8gkiq3YsYqBP308zS6x-xFknO9y-5n-jd4tfQQiExr3RaS6EYYE4uwx9OgXaHYqABW8OgjR-suhMkcvPylyuLIZb3r99PE38O43YHUGWzvXfZDQUfSQO72VVwksuZ4RUKUk5gzlGjbO2nM5o8cz-OuUftNBfjWNiJGgCBtj5ovAIe4cbZr1tOmpQ6-2sVzKgpfvOvlS90LJkiyjSF8quFLpT4ibnUxQir4H9aSzfeVTQauoSsDk6yiZKU-GnHxcDrb1mQSVP7exRckqhONKIRcLVAwG5A3EFWa3VhHYBxdGQ65KZVuq6xVzVT3w6q1ZOgp6qn0jfqd-dT-13prIOs4PB6hyvGhL9fKkDujVGzUrQa_TwN_adS0GTzEs_iSXqkhqmsLaFmT8KQYJR9OpHBoo4UcWO6m-0vC-s3g4K-RISBdx9nD0279XNFNRNm3YP1JHAt263FwCoORTNU7m6kpk8l9Zo0gKg8syZjxJ6iO3wEX0f3LsVQwtQEF2QNcIQy63qb41kFpdWcLu9Y-vWB8kDqHuxZpFhR_atQwimUMSZskbhJx8Z6hYuLxfxIe3FgN09PHrPWUf0uzBSZoqIBshDH10O2lyP3g9KjUGrra5kgMl0D16-X_lIUFQ8Cwj4vgeYsyaT-gQsoMCx3IKR-61ggXOAeaqdOi8hGzKCqjGYtbduTeZiJaJyGqQVBdD-8-kaxrV7utQpLpgmkM1I_bdDFgfDaqzBE-QaFiMxS9auZ4ttXXUkl95C7IS1y3-ef-ipKrC5McUB2zqC5En-kxHbDzjhuWTgoY&v=404";
        spider.addUrl(testPage);
//        spider.addUrl(testPage2);
//        spider.addUrl(testAdvPage);
    }

    private static void process_Normal(Spider spider, String startPage, int pageNumber) {//爬虫方案_normal
        for (int i = 1; i <= pageNumber; i++) {
            spider.addUrl(startPage + i);
        }
        spider.addPipeline(new MyJsonPipeline("D:\\data collection\\webmagic"));
    }

    private static void proccess_Lucene(Spider spider, String startPage, int pageNumber,String lucenePath) {
        for (int i = 1; i <= pageNumber; i++) {
            spider.addUrl(startPage + i);
        }
        spider.addPipeline(new MyLucenePipeline());
        Lucene.getInstance().init(lucenePath);//初始化lucene路径
    }

    private static void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
