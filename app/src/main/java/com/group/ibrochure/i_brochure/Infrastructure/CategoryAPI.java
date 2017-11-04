package com.group.ibrochure.i_brochure.Infrastructure;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.Category.ICategoryRepo;
import com.group.ibrochure.i_brochure.UI.Category.CategoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yogi on 02/11/2017.
 */

public class CategoryAPI extends BaseAPI<Category> implements ICategoryRepo {

    public CategoryAPI(Context context) {
        super(context);
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
