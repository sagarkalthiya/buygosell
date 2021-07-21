package com.trooople.godofhell.buygosell.Model;

/**
 * Created by God of hell on 2018-04-27.
 */

public class My_order {


    private String product_name = null;
    private Boolean isType = null;
    private String date =null;
    private String price =null;
    private String discription =null;
    private int image;


    public String getP_name() {
        return product_name;
    }

    public void setP_name(String p_name) {
        this.product_name = p_name;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getdiscription() {
        return discription;
    }

    public void setdiscription(String discription) {
        this.discription = discription;
    }

    public int getimage() {
        return image;
    }

    public void setimage(int image) {
        this.image = image;
    }

    public Boolean getType() {
        return isType;
    }

    public void setType(Boolean sender) {
        isType = sender;
    }
}
