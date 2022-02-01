package com.official.satyaadmin;

import com.official.satyaadmin.lists.product_detail;

import java.util.List;

public class main_activity_section_list {
    String name;
    List<product_detail> list;

    public main_activity_section_list(){

    }
    public main_activity_section_list(String name, List<product_detail> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<product_detail> getList() {
        return list;
    }

    public void setList(List<product_detail> list) {
        this.list = list;
    }
}

