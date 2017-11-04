package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

/**
 * Created by Yogi on 02/11/2017.
 */

public abstract class BaseAPI<T extends EntityBase> implements IRepository<T> {
    protected RequestQueue requestQueue;
    protected Context context;

    public BaseAPI(RequestQueue requestQueue, Context context) {
        this.requestQueue = requestQueue;
        this.context = context;
    }
}
