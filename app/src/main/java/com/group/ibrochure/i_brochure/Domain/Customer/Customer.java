package com.group.ibrochure.i_brochure.Domain.Customer;

import com.group.ibrochure.i_brochure.Common.EntityBase;

/**
 * Created by Yogi on 02/11/2017.
 */

public class Customer extends EntityBase {
    private String name;
    private String contact;
    private String telp;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
