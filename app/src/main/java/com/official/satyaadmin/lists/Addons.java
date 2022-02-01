package com.official.satyaadmin.lists;

public class Addons {
    String name;
    int price;
    String key;

    public Addons(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Addons(String name, int price,String key) {
        this.name = name;
        this.price=price;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
