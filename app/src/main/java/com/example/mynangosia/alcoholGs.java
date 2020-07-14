package com.example.mynangosia;

import java.io.Serializable;

public class alcoholGs implements Serializable {

    private String date;
    private String time;
    private String description;
    private String productName;
    private String value;
    private String pk;
    private String Pic;
    public static String total_price;

    public alcoholGs(String date, String time, String description, String productName, String value, String pk, String pic, String total_price) {
        alcoholGs.total_price = total_price;
        this.setDate(date);
        this.setTime(time);
        this.setDescription(description);
        this.setProductName(productName);
        this.setValue(value);
        this.setPk(pk);
        setPic(pic);
    }


    public alcoholGs() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }


    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}