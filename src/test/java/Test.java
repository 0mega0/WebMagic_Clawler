
import clawer.Main;
import clawer.model.PhonePrice;
import clawer.util.JDCommentHandler;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Test {
    private static final Logger log = Logger.getLogger(Test.class);


    public static void main(String[] args) throws IOException {
//
//        JDCommentHandler jdc = new JDCommentHandler("100007958748");
//        String url = "https://item.jd.com/100008348530.html";
//        log.info(url.substring(20,url.length()-5));
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        log.info(formater.format(date));
        log.info(date.getTime());
    }
}
