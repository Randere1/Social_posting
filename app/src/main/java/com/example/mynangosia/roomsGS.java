package com.example.mynangosia;

import java.io.Serializable;

public class roomsGS  implements Serializable {
    private String date;
    private String time;
    private String productName;
    private String value;
    private String pk;
    private String Pic;

    public roomsGS() {
    }



    public roomsGS(String date, String time, String productName, String value, String pk, String pic) {
        this.setDate(date);
        this.setTime(time);
        this.setProductName(productName);
        this.setValue(value);
        this.setPk(pk);
        setPic(pic);
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
}
