package com.example.mynangosia;

import java.io.Serializable;

public class user_with_orderGs implements Serializable {

    private  String date;
    private  String odate;
    private String time;
    private String otime;
    private String state;
    private String amount;

    public user_with_orderGs(String currentUserId) {
        CurrentUserId = currentUserId;
    }

    private String CurrentUserId;

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        CurrentUserId = currentUserId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    private String transaction;
    private String name;
    private String pk;
    private String phone;
    private String payment_method;
    private String Longitude;
    private String Latitude;

    public user_with_orderGs(String date, String odate, String time, String otime, String state, String amount, String transaction, String name, String pk, String phone, String payment_method, String longitude, String latitude) {
        this.date = date;
        this.odate = odate;
        this.time = time;
        this.otime = otime;
        this.state = state;
        this.amount = amount;
        this.transaction = transaction;
        this.name = name;
        this.pk = pk;
        this.phone = phone;
        this.payment_method = payment_method;
        Longitude = longitude;
        Latitude = latitude;
    }



    public user_with_orderGs() {
    }




}
