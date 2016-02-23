package com.jchunch.dynamicfeed.model;

/**
 * Created by jchunch on 2/17/16.
 */
public class SmallTile {

    private String header;
    private String body;
    private String imageUrl;

    public SmallTile(String header, String body, String imageUrl) {
        this.header = header;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
