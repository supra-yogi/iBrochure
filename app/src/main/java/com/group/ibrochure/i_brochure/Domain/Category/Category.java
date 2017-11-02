package com.group.ibrochure.i_brochure.Domain.Category;

import com.group.ibrochure.i_brochure.Common.EntityBase;

/**
 * Created by Yogi on 02/11/2017.
 */

public class Category extends EntityBase {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
