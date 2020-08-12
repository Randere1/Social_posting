package com.example.mynangosia;

import java.io.Serializable;

public class user_with_orderGs implements Serializable {

    private  String date;
    private String time;
    private String amount;
    private String transaction;
    private String name;
    private String pk;
    private String phone;
    private String payment_method;
    private String Longitude;
    private String Latitude;

    public user_with_orderGs(String date, String time, String amount, String transaction, String name, String pk, String phone, String payment_method, String longitude, String latitude) {
        this.setDate(date);
        this.setTime(time);
        this.setAmount(amount);
        this.setTransaction(transaction);
        this.setName(name);
        this.setPk(pk);
        this.setPhone(phone);
        this.setPayment_method(payment_method);
        setLongitude(longitude);
        setLatitude(latitude);
    }



    public user_with_orderGs() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
