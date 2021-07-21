package com.trooople.godofhell.buygosell.Model;

public class Best_sellers_model  {

    public String  product_id,product_title,product_description;
    public String  product_price,product_currency;
    public String  product_photo_id,product_photo_path,product_photo_extension;
    public Best_sellers_model(){

    }

    public String getProduct_Id() {
        return product_id;
    }
    public void setProduct_Id(String P_id) {
        this.product_id = P_id;
    }

    public String getProduct_Title() {
        return product_title;
    }
    public void setProduct_Title(String P_title) {
        this.product_title = P_title;
    }

    public String getProduct_Description() {
        return product_description;
    }
    public void setProduct_Description(String P_description) {
        this.product_description = P_description;
    }

    public String getProduct_Price() {
        return product_price;
    }
    public void setProduct_Price(String P_price) {
        this.product_price = P_price;
    }

    public String getProduct_Currency() {
        return product_currency;
    }
    public void setProduct_Currency(String P_currency) {
        this.product_currency = P_currency;
    }


    public String getProduct_Photo_Id() {
        return product_photo_id;
    }
    public void setProduct_Photo_Id(String P_photo_id) {
        this.product_photo_id = P_photo_id;
    }

    public String getProduct_Photo_Path() {
        return product_photo_path;
    }
    public void setProduct_Photo_Path(String P_photo_path) {
        this.product_photo_path = P_photo_path;
    }

    public String getProduct_Photo_Extension() {
        return product_photo_extension;
    }
    public void setProduct_Photo_Extension(String P_photo_extension) {
        this.product_photo_extension = P_photo_extension;
    }


}
