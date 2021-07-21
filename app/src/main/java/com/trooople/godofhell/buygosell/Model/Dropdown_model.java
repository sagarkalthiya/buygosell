package com.trooople.godofhell.buygosell.Model;

public class Dropdown_model  {
    //  @SerializedName("name")
    public String name,id,short_name,phone_code;
    public Dropdown_model(){}
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getshort_name() {
        return short_name;
    }
    public void setshort_name(String short_name) {
        this.short_name = short_name;
    }
    public String getphone_code() {
        return phone_code;
    }
    public void setphone_code(String phone_code) {
        this.phone_code = phone_code;
    }
}