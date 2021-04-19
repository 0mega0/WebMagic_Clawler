package clawer.util;

import clawer.model.PhonePrice;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Date 2021/4/18
 * @author 0mega_0
 *JD Price Page https://p.3.cn/prices/mgets?skuIds=J_100008348530
 */
public class JDPriceHandler {
    private static final Logger log = Logger.getLogger(JDPriceHandler.class);
    private URL targetUrl;
    private PhonePrice pp;
    public JDPriceHandler(String url) {
        try{
            this.targetUrl = new URL(url);
        } catch(IOException e){
            log.error(e.toString() + "on URL[" + targetUrl + "]");
        }

        process(targetUrl);
    }
    private void process(URL targetUrl) {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
            String js="",inputLine;
            while ((inputLine = in.readLine()) != null)
                js+=inputLine;
            in.close();
            js = js.replace("[", "").replace("]", "");
            Gson g = new Gson();
            PhonePrice pp = (PhonePrice) g.fromJson(js, PhonePrice.class);
            this.pp = pp;
        } catch(IOException e){
            log.error(e.toString() + "on URL[" + targetUrl + "]");
        }

    }
    public PhonePrice getPhonePrice(){
        return this.pp;
    }

}
