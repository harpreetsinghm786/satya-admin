package com.official.satyaadmin.lists;



public class common_list {
    String name;
    String url;
    String tag;
    String likes;
    String key;
    public common_list() {
    }


    public common_list(String name, String url,String tag,String likes,String key) {

        this.name=name;
        this.url=url;
        this.key=key;
        this.likes=likes;
        this.tag=tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
