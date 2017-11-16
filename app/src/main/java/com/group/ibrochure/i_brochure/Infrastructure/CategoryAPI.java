package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.Category.ICategoryRepo;

/**
 * Created by Yogi on 02/11/2017.
 */

public class CategoryAPI extends BaseAPI<Category> implements ICategoryRepo {

    public CategoryAPI(Context context) {
        super(context);
    }

    @Override
    public void Save(ResponseCallBack responseCallBack, Category entity) {

    }

    @Override
    public Category CreateNew() {
        return new Category();
    }

    @Override
    public String GetUrl() {
        return URLs.URL_CATEGORY;
    }
}
