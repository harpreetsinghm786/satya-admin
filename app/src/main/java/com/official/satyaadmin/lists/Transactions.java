package com.official.satyaadmin.lists;

import java.util.Date;

public class Transactions {
    String transactionid,category;
    Date date;
    int amount;

    public Transactions(){

    }

    public Transactions(String transactionid, String category, Date date, int amount) {
        this.category=category;
        this.transactionid = transactionid;
        this.date = date;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date_of_topup) {
        this.date = date_of_topup;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
