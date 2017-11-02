package com.group.ibrochure.i_brochure.UI.model;

/**
 * Created by KinKin on 10/31/2017.
 */

public class Brochure {

    private String Name;
    private String Telp;
    private String Address;
    private String ImgUrl;


    public Brochure() {
    }

    public Brochure(String name, String telp, String address, String imgUrl) {

        Name = name;
        Telp = telp;
        Address = address;
        ImgUrl = imgUrl;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTelp() {
        return Telp;
    }

    public void setTelp(String telp) {
        Telp = telp;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
