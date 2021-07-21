package com.trooople.godofhell.buygosell.Model;

/**
 * Created by God of hell on 2018-04-30.
 */

public class Address_model {

    private String address_id, address_title, address, post_code, telephone, country, state, city, bio;


    public String getaddress_id() {
        return address_id;
    }
    public void setaddress_id(String adress_id) {
        this.address_id= adress_id;
    }

    public String getaddress_title() {
        return address_title;
    }
    public void setaddress_title(String adress_title) {
        this.address_title = adress_title;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String adress) {
        this.address = adress;
    }

    public String getpost_code() {
        return post_code;
    }
    public void setpost_code(String post_code) {
        this.post_code = post_code;
    }

    public String gettelephone() {
        return telephone;
    }

    public void settelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }


    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }


}
