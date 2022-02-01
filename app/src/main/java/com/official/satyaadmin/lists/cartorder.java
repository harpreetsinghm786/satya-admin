package com.official.satyaadmin.lists;

import java.util.Date;
import java.util.List;

public class cartorder {
    String username,givenphonenumber,extraphonenumber, orderid,status,userpicurl,address,deliverymode,paymentmode,reason_of_cancellation;
    List<cart_list>list;

    int deliverycharges,servicetax,prog_status,grandtotal;
    Date orderDate,dateofcancellation,date_of_delivery;
    public cartorder(){

    }
    public cartorder(int grandtotal, String username, String givenphonenumber, String extraphonenumber, int deliverycharges, int servicetax, String orderid, String status, String userpicurl, String address, String deliverymode, String paymentmode, List<cart_list> list, int prog_status, Date orderDate, Date dateofcancellation, String reason_of_cancellation, Date date_of_delivery) {
        this.grandtotal = grandtotal;
        this.username = username;
        this.date_of_delivery=date_of_delivery;
        this.reason_of_cancellation=reason_of_cancellation;
        this.status=status;
        this.orderDate=orderDate;
        this.prog_status=prog_status;
        this.givenphonenumber = givenphonenumber;
        this.extraphonenumber = extraphonenumber;
        this.deliverycharges = deliverycharges;
        this.servicetax = servicetax;
        this.dateofcancellation=dateofcancellation;
        this.orderid = orderid;
        this.list= (List<cart_list>) list;
        this.userpicurl = userpicurl;
        this.address = address;
        this.deliverymode = deliverymode;
        this.paymentmode = paymentmode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getProg_status() {
        return prog_status;
    }

    public void setProg_status(int prog_status) {
        this.prog_status = prog_status;
    }

    public int getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(int grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGivenphonenumber() {
        return givenphonenumber;
    }

    public void setGivenphonenumber(String givenphonenumber) {
        this.givenphonenumber = givenphonenumber;
    }

    public String getExtraphonenumber() {
        return extraphonenumber;
    }

    public void setExtraphonenumber(String extraphonenumber) {
        this.extraphonenumber = extraphonenumber;
    }

    public int getDeliverycharges() {
        return deliverycharges;
    }

    public void setDeliverycharges(int deliverycharges) {
        this.deliverycharges = deliverycharges;
    }

    public int getServicetax() {
        return servicetax;
    }

    public void setServicetax(int servicetax) {
        this.servicetax = servicetax;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserpicurl() {
        return userpicurl;
    }

    public void setUserpicurl(String userpicurl) {
        this.userpicurl = userpicurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliverymode() {
        return deliverymode;
    }

    public void setDeliverymode(String deliverymode) {
        this.deliverymode = deliverymode;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public List<cart_list> getList() {
        return list;
    }

    public void setList(List<cart_list> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Date getDateofcancellation() {
        return dateofcancellation;
    }

    public void setDateofcancellation(Date dateofcancellation) {
        this.dateofcancellation = dateofcancellation;
    }

    public String getReason_of_cancellation() {
        return reason_of_cancellation;
    }

    public void setReason_of_cancellation(String reason_of_cancellation) {
        this.reason_of_cancellation = reason_of_cancellation;
    }

    public Date getDate_of_delivery() {
        return date_of_delivery;
    }

    public void setDate_of_delivery(Date date_of_delivery) {
        this.date_of_delivery = date_of_delivery;
    }
}

