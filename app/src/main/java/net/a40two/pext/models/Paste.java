package net.a40two.pext.models;

import org.parceler.Parcel;

/**
 * Created by slaughtr on 6/2/17.
 */

@Parcel
public class Paste {
    String title;
    String url;
    String date;
    String expires;
    String hits;
    String key;
    String size;

    //this isn't in constructor, as it requires an additional API call
    String body;

    public Paste(String title, String key, String date, String hits, String size, String expires) {
        this.title = title;
        this.key = key;
        this.date = date;
        this.hits = hits;
        this.size = size;
        this.expires = expires;
    }

    public Paste() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
