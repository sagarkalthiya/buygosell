package com.trooople.godofhell.buygosell.Model;

/**
 * Created by God of hell on 2018-04-17.
 */

public class Shopping_cart_model {


    private String product_name = null;
    private Boolean isType = null;


    public String getP_name() {
        return product_name;
    }

    public void setP_name(String p_name) {
        this.product_name = p_name;
    }


    public Boolean getType() {
        return isType;
    }

    public void setType(Boolean sender) {
        isType = sender;
    }
}
