package com.official.satyaadmin.lists;

import java.util.ArrayList;
import java.util.List;

public class product_detail {
    String name;
    int price;
    String Genre;
    String sub_category;
    String item_components;
    String postid;
    int not_price,deals_of_the_day;
    float rating;
    String status;
    String tags;
    String url_main,url2,url3;

    int percentage_off;



    public product_detail(){

    }

    public product_detail(String name, int price, String Genre, String item_components, int not_price, int rating, String status, int percentage_off, String url_main, String url2, String url3, String postid, int deals_of_the_day,String sub_category,String tags) {
        this.name = name;
        this.price = price;
        this.Genre = Genre;
        this.tags=tags;
        this.item_components = item_components;
        this.not_price = not_price;
        this.rating = rating;
        this.status = status;
        this.sub_category=sub_category;
        this.postid=postid;
        this.percentage_off = percentage_off;
        this.url_main=url_main;
        this.url2=url2;
        this.url3=url3;
        this.deals_of_the_day=deals_of_the_day;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getGenre() {
        return Genre;
    }

    public String getItem_components() {
        return item_components;
    }

    public int getNot_price() {
        return not_price;
    }

    public float getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl_main() {
        return url_main;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public int getPercentage_off() {
        return percentage_off;
    }

    public String getPostid() {
        return postid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setItem_components(String item_components) {
        this.item_components = item_components;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setNot_price(int not_price) {
        this.not_price = not_price;
    }

    public int getDeals_of_the_day() {
        return deals_of_the_day;
    }

    public void setDeals_of_the_day(int deals_of_the_day) {
        this.deals_of_the_day = deals_of_the_day;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUrl_main(String url_main) {
        this.url_main = url_main;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public void setPercentage_off(int percentage_off) {
        this.percentage_off = percentage_off;
    }
}
