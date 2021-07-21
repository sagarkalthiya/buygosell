package com.trooople.godofhell.buygosell.Model;

public class My_Basket_model  {

    public String  user_basket_id,user_basket_publication_id,user_basket_buyer_id,user_basket_seller_id,user_basket_amount,product_publication_id;

    public String  basket_product_title,basket_product_discription,basket_product_catagory_id,basket_product_option_id,basket_product_user_id;

    public int basket_product_price;

    public String  basket_product_from_country_id,basket_product_to_country_id,basket_product_start_date,basket_product_end_date,basket_product_currency;

    public String  product_piece,product_piece_type,product_status,product_photo_id,basket_photo_path,basket_photo_extension;

    public My_Basket_model(){

    }

    public String getUser_Basket_Id() {
        return user_basket_id;
    }
    public void setUser_Basket_Id(String u_basket_id) {
        this.user_basket_id = u_basket_id;
    }

    public String getUser_Basket_Publication_Id() {
        return user_basket_publication_id;
    }
    public void setUser_Basket_Publication_Id(String u_basket_pb_id) {
        this.user_basket_publication_id = u_basket_pb_id;
    }

    public String getUser_Basket_Buyer_Id() {
        return user_basket_buyer_id;
    }
    public void setUser_Basket_Buyer_Id(String u_basket_byer_id) {
        this.user_basket_buyer_id = u_basket_byer_id;
    }

    public String getUser_Basket_Seller_Id() {
        return user_basket_seller_id;
    }
    public void setUser_Basket_Seller_Id(String u_basket_seller_id) {
        this.user_basket_seller_id = u_basket_seller_id;
    }

    public String getUser_Basket_Amount() {
        return user_basket_amount;
    }
    public void setUser_Basket_Amount(String u_basket_amount) {
        this.user_basket_amount = u_basket_amount;
    }

    public String getProduct_Publication_Id() {
        return product_publication_id;
    }
    public void setProduct_Publication_Id(String u_basket_pid) {
        this.product_publication_id = u_basket_pid;
    }

    public String getBasket_Product_Title() {
        return basket_product_title;
    }
    public void setBasket_Product_Title(String u_basket_ptitle) {
        this.basket_product_title = u_basket_ptitle;
    }

    public String getBasket_Product_Discription() {
        return basket_product_discription;
    }
    public void setBasket_Product_Discription(String u_basket_pidiscription) {
        this.basket_product_discription = u_basket_pidiscription;
    }

    public String geBasket_Product_User_Id() {
        return basket_product_user_id;
    }
    public void setBasket_Product_User_Id(String u_basket_puserid) {
        this.basket_product_user_id = u_basket_puserid;
    }

    public String getBasket_Product_From_Country_Id() {
        return basket_product_from_country_id;
    }
    public void setBasket_Product_From_Country_Id(String Basket_P_from_country_id) {
        this.basket_product_from_country_id = Basket_P_from_country_id;
    }

    public String getBasket_Product_To_Country_Id() {
        return basket_product_to_country_id;
    }
    public void setBasket_Product_To_Country_Id(String Basket_P_to_country_id) {
        this.basket_product_to_country_id = Basket_P_to_country_id;
    }

    public String getBasket_Product_Start_Date() {
        return basket_product_start_date;
    }
    public void setBasket_Product_Start_Date(String Basket_P_start_date) {
        this.basket_product_start_date = Basket_P_start_date;
    }

    public String getBasket_Product_End_Date() {
        return basket_product_end_date;
    }
    public void setBasket_Product_End_Date(String Basket_P_end_date) {
        this.basket_product_end_date = Basket_P_end_date;
    }

    public int getBasket_Product_Price() {
        return basket_product_price;
    }
    public void setBasket_Product_Price(int Basket_P_price) {
        this.basket_product_price = Basket_P_price;
    }

    public String getBasket_Product_Currency() {
        return basket_product_currency;
    }
    public void setBasket_Product_Currency(String Basket_P_currency) {
        this.basket_product_currency = Basket_P_currency;
    }
/*
    public String getProduct_Piece() {
        return product_piece;
    }
    public void setProduct_Piece(String P_piece) {
        this.product_piece = P_piece;
    }

    public String getProduct_Piece_Type() {
        return product_piece_type;
    }
    public void setProduct_Piece_Type(String P_piece_type) {
        this.product_piece_type = P_piece_type;
    }*/

 /*   public String getProduct_Status() {
        return product_status;
    }
    public void setProduct_Status(String P_status) {
        this.product_status = P_status;
    }

    public String getProduct_Photo_Id() {
        return product_photo_id;
    }
    public void setProduct_Photo_Id(String P_photo_id) {
        this.product_photo_id = P_photo_id;
    }*/

    public String getBasket_Photo_Path() {
        return basket_photo_path;
    }
    public void setBasket_Photo_Path(String basket_photo_path) {
        this.basket_photo_path = basket_photo_path;
    }

    public String getBasket_Photo_Extension() {
        return basket_photo_extension;
    }
    public void setBasket_Photo_Extension(String basket_photo_extension) {
        this.basket_photo_extension = basket_photo_extension;
    }


}