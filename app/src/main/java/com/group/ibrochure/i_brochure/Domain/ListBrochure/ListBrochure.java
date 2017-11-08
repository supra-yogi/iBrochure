package com.group.ibrochure.i_brochure.Domain.ListBrochure;

import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yogi on 02/11/2017.
 */

public class ListBrochure extends EntityBase {
    private String title;
    private String telp;
    private String address;
    private String description;
    private Category category;
    private UserAccount userAccount;
    private List<ListBrochurePicture> listBrochurePictures;

    public ListBrochure() {
        listBrochurePictures = new ArrayList<ListBrochurePicture>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void AddPicture(String pictureName, String pictureBase64) {
        ListBrochurePicture ent = new ListBrochurePicture();
        ent.setPictureName(pictureName);
        ent.setPictureBase64(pictureBase64);

        listBrochurePictures.add(ent);
    }

    public void AddPicture(ListBrochurePicture listBrochurePicture) {
        AddPicture(listBrochurePicture.getPictureName(), listBrochurePicture.getPictureBase64());
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
