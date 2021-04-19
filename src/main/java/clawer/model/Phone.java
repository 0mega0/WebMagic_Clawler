package clawer.model;

/**
 * Date 2021/4/15
 * @author 0mega_0
 *
 */
public class Phone {
    private String id;
    private String url;
    private String imgUrl;
    private String title;
    private String price;
    private String comment_count;
    private String comment_rate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getComment_rate() {
        return comment_rate;
    }

    public void setComment_rate(String comment_rate) {
        this.comment_rate = comment_rate;
    }
}
