package clawer.pageProcessors;

import clawer.model.Phone;
import clawer.model.PhonePrice;
import clawer.util.JDCommentHandler;
import clawer.util.JDPriceHandler;
import clawer.util.PipeOutputFactor;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
/**
 * Date 2021/4/16
 * @author 0mega_0
 *
 */
public class JDPage implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");
    private static final Logger log = Logger.getLogger(JDPage.class);
    private Phone phone;

    public void process(Page page) {
        String url = page.getRequest().getUrl();
        String xpathBaseList = "//div[@id='J_goodsList']/ul[@class='gl-warp clearfix']/li[@class='gl-item']/*/";

        if(url.startsWith("https://list.jd.com/")){
            log.info("dealing with list page [" + url + "]");
            List<String> items = page.getHtml().xpath(xpathBaseList + "div[@class='p-img']/a/@href").all();
            log.info("抓到了item链接"+items.size()+"个");

            for(String pageURL:items){
                page.addTargetRequest(pageURL);
            }
            page.addTargetRequest(items.get(0));
            page.setSkip(true);
        } else if(url.startsWith("https://item.jd.com/")){
            //log.info("item page [" + url + "]");
            phone = new Phone();
            phone.setId(url.replace("https://item.jd.com/", "").replace(".html", ""));
            phone.setUrl(url);
            phone.setImgUrl(page.getHtml().xpath("//*[@id='spec-img']/@data-origin").get());
            phone.setTitle(page.getHtml().xpath("//*[@class='sku-name']/text()").get().trim());

            JDPriceHandler jdp = new JDPriceHandler("https://p.3.cn/prices/mgets?skuIds=J_"+phone.getId());
            PhonePrice pp = jdp.getPhonePrice();
            phone.setPrice(pp.getCurrentPrice());

            JDCommentHandler jdc = new JDCommentHandler(phone.getId());
            phone.setComment_rate(jdc.getCommentRate());
            phone.setComment_count(jdc.getCommentCount());
            //图片部分，未处理
            //log.info(page.getHtml().xpath("//*[@id='spec-img']/@data-origin").all());

            //将处理过的Phone都添加到PhoneList中，以待后续使用
            PipeOutputFactor.getInstance().getPhoneList().add(phone);
            page.putField("PHONES_INFO",phone);

        }
        //之前想的是让所有的phone全都存到同一个文件中，方便统计观看。后面需求变化，要每个手机存在一个文件里，这段代码就废弃了。
        //page.putField("PHONES_INFO",PipeOutputFactor.getInstance().getPhoneList());

    }

    public Site getSite() {
        return this.site;
    }
}
