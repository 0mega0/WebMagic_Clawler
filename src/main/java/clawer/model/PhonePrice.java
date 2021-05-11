package clawer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Date 2021/4/18
 * @author 0meag_0
 * JavaBean for JD Phone price page
 * 给京东手机价格页写的JavaBean
 * JD PhonePrice Page Example [https://p.3.cn/prices/mgets?skuIds=J_100008348530]
 */

public class PhonePrice {

    @SerializedName("p")
    private String CurrentPrice;
    @SerializedName("op")
    private String GuidancePrice;
    @SerializedName("cbf")
    private String cbf;
    @SerializedName("id")
    private String ID;
    @SerializedName("m")
    private String HighestPrice;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        CurrentPrice = currentPrice;
    }

    public String getGuidancePrice() {
        return GuidancePrice;
    }

    public void setGuidancePrice(String guidancePrice) {
        GuidancePrice = guidancePrice;
    }

    public String getHighestPrice() {
        return HighestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        HighestPrice = highestPrice;
    }

    public String getCbf() {
        return cbf;
    }

    public void setCbf(String cbf) {
        this.cbf = cbf;
    }
}
