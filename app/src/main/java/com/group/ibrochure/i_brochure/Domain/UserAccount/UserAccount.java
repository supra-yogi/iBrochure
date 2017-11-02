package com.group.ibrochure.i_brochure.Domain.UserAccount;

import com.group.ibrochure.i_brochure.Common.EntityBase;

/**
 * Created by Yogi on 02/11/2017.
 */

public class UserAccount extends EntityBase {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
