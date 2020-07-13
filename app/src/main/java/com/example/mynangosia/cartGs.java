package com.example.mynangosia;


public class cartGs {

    private String productName;
    private String pk;
    private String Value;
    private String Pic;

    public cartGs(String total) {
        this.setTotal(total);
    }

    private String total;

    public cartGs() {
    }

    private String quantity;
    private String description;

    public cartGs(String productName, String pk, String value, String pic, String quantity, String description) {
        this.setProductName(productName);
        this.setPk(pk);
        setValue(value);
        setPic(pic);
        this.setQuantity(quantity);
        this.setDescription(description);
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
