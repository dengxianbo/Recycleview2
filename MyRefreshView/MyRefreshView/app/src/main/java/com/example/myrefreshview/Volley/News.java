package com.example.myrefreshview.Volley;

/**
 * Created by 工作 on 2016/8/1.
 */
public class News {
    String title;
    String data;
    String twotitle;
    String pic;
    String url;

    public News(String title, String data, String twotitle, String pic, String url) {
        this.title = title;
        this.data = data;
        this.twotitle = twotitle;
        this.pic = pic;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTwotitle() {
        return twotitle;
    }

    public void setTwotitle(String twotitle) {
        this.twotitle = twotitle;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", twotitle='" + twotitle + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
