package com.example.profdev11;

public class Pub {

    private int pub_id;
    private String name;
    private String street_name;
    private String postcode;



    public Pub(int pub_id, String name, String street_name, String postcode)
    {
        this.pub_id = pub_id;
        this.name = name;
        this.street_name = street_name;
        this.postcode = postcode;
    }

    public int getPub_id() {
        return pub_id;
    }

    public void setPub_id(int iD) {
        this.pub_id = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet_Name() {
        return street_name;
    }

    public void setStreet_Name(String street_name) {
        this.street_name = street_name;
    }

    public String getPostCode() {
        return postcode;
    }

    public void setPostCode(String postcode) {
        this.postcode = postcode;
    }
}
