package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.Category.ICategoryRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yogi on 02/11/2017.
 */

public class CategoryAPI extends BaseAPI<Category> implements ICategoryRepo {

    public CategoryAPI(RequestQueue requestQueue, Context context) {
        super(requestQueue, context);
    }


    @Override
    public Category GetById(int id) {
        final Category entity = new Category();
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_CATEGORY + id, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                entity.setId(Integer.parseInt(jsonObject.get("Id").toString()));
                                entity.setCode(jsonObject.get("Code").toString());
                                entity.setName(jsonObject.get("Name").toString());
                            }
                        } catch (JSONException e) {
                            Log.d("Error", e.getMessage());
                        }
                        // Retrieves the string labeled "colorName" and "description" from
                        //the response JSON Object
                        //and converts them into javascript objects
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        requestQueue.add(getRequest);
        return entity;
    }

    @Override
    public List<Category> GetAll() {
        final List<Category> entities = new ArrayList<>();
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_CATEGORY, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                Category entity = new Category();

                                entity.setId(Integer.parseInt(jsonObject.get("Id").toString()));
                                entity.setCode(jsonObject.get("Code").toString());
                                entity.setName(jsonObject.get("Name").toString());

                                entities.add(entity);
                            }
                        } catch (JSONException e) {
                            Log.d("Error", e.getMessage());
                        }
                        // Retrieves the string labeled "colorName" and "description" from
                        //the response JSON Object
                        //and converts them into javascript objects
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        requestQueue.add(getRequest);
        return entities;
    }

    @Override
    public void Save(final Category entity) {
        if (entity.getId() == 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, URLs.URL_CATEGORY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {         // Adding parameters
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Code", entity.getCode());
                    params.put("Name", entity.getName());

                    return params;
                }
            };
            requestQueue.add(postRequest);
        } else {
            StringRequest putRequest = new StringRequest(Request.Method.PUT, URLs.URL_CATEGORY,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String> ();
                    params.put("Code", entity.getCode());
                    params.put("Name", entity.getName());

                    return params;
                }
            };

            requestQueue.add(putRequest);
        }
    }

    @Override
    public void Delete(int id) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URLs.URL_CATEGORY + id,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        requestQueue.add(deleteRequest);
    }
}
