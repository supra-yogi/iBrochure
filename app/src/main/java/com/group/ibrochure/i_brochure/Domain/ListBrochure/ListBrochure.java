package com.group.ibrochure.i_brochure.Domain.ListBrochure;

import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yogi on 02/11/2017.
 */

public class ListBrochure extends EntityBase {
    private String title;
    private String telephone;
    private String address;
    private String description;
    private String pictureFront;
    private String pictureBack;
    private String postingDate;
    private Category category;
    private UserAccount userAccount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getPictureFront() {
        return pictureFront;
    }

    public void setPictureFront(String pictureFront) {
        this.pictureFront = pictureFront;
    }

    public String getPictureBack() {
        return pictureBack;
    }

    public void setPictureBack(String pictureBack) {
        this.pictureBack = pictureBack;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
}
