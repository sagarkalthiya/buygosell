package com.trooople.godofhell.buygosell.Model;

/**
 * Created by God of hell on 2018-04-16.
 */

public class Product_model {

    private String product_name;
    private String price_name;
    private int image;

    public Product_model(int imageResource, String product_name_s, String price_name_s) {
        image = imageResource;
        product_name = product_name_s;
        price_name = price_name_s;
    }

    public String getproduct_name() {
        return product_name;
    }

    public void setproduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getprice_name() {
        return price_name;
    }

    public void setprice_name(String price_name) {
        this.price_name = price_name;
    }
    public int getimage() {
        return image;
    }

    public void setimage(int image) {
        this.image = image;
    }
}
