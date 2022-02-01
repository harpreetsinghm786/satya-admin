package com.official.satyaadmin.lists;

import com.official.satyaadmin.lists.Addons;

import java.util.List;

public class cart_list {
    String name;
    int price;
    int oldprice;
    int quantity;
    String image;
    String postid;
    List<Addons>addons;
  public cart_list(){

  }
    public cart_list(String name, int price, int quantity, String image, String postid, List<Addons>addons, int oldprice) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image=image;
        this.postid=postid;
        this.addons=addons;
        this.oldprice=oldprice;

    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {

      return image;
    }

    public String getPostid() {
        return postid;
    }

    public List<Addons> getAddons() {
        return addons;
    }

    public int getOldprice() {
        return oldprice;
    }
}
