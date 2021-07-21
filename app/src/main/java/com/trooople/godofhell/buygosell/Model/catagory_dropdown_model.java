package com.trooople.godofhell.buygosell.Model;

public class catagory_dropdown_model {
    //  @SerializedName("name")
    public String name ,en_name,cat_id,cat_discription,cat_icon;
    public  String  cat_img;
    public catagory_dropdown_model(){}
    /*  public model_ad(String name ,String Id, String sortname, String Phonecode) {
          this.name = name;
          this.id = Id;
          this.short_name = sortname;
          this.phone_code = Phonecode;
      }*/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEn_name() {
        return en_name;
    }
    public void setEn_name(String enname) {
        this.en_name = enname;
    }

    public String getId() {
        return cat_id;
    }
    public void setId(String id) {
        this.cat_id = id;
    }
    public String getCat_discription() {
        return cat_discription;
    }
    public void setCat_discription(String discription) {
        this.cat_discription = discription;
    }
    public String getCat_img() {
        return cat_img;
    }
    public void setCat_img(String catimg) {
        this.cat_img = catimg;
    }

    public String getCat_icon() {
        return cat_icon;
    }
    public void setCat_icon(String caticon) {
        this.cat_icon = caticon;
    }
}