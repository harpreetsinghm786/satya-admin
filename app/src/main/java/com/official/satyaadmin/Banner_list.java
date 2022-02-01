package com.official.satyaadmin;

import com.official.satyaadmin.lists.product_detail;

import java.util.List;

public class Banner_list {
    String banner_key,Banner_url;
    List<product_detail> list;

    public Banner_list(){

    }

    public Banner_list(String banner_key, String banner_url,List<product_detail> list) {
        this.banner_key = banner_key;
        Banner_url = banner_url;
        this.list =  list;
    }


    public List<product_detail> getList() {
        return list;
    }

    public void setList(List<product_detail> list) {
        this.list = list;
    }

    public String getBanner_key() {
        return banner_key;
    }

    public void setBanner_key(String banner_key) {
        this.banner_key = banner_key;
    }

    public String getBanner_url() {
        return Banner_url;
    }

    public void setBanner_url(String banner_url) {
        Banner_url = banner_url;
    }
}

