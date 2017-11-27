package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group.ibrochure.i_brochure.Common.EntityBase;
import com.group.ibrochure.i_brochure.Common.IRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 02/11/2017.
 */

public abstract class BaseAPI<T extends EntityBase> implements IRepository<T> {
    protected Context context;
    protected String tag;
    protected String url;

    public BaseAPI(Context context) {
        this.context = context;
        this.tag = context.getClass().getSimpleName();
        url = GetUrl();
    }

    @Override
    public void GetById(final ResponseCallBack responseCallBack, int id) {
        JsonArrayRequest request = new JsonArrayRequest(url + id,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallBack.onResponse(response);
                        Log.d(tag, "Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void GetAll(final ResponseCallBack responseCallBack) {
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        responseCallBack.onResponse(response);
                        Log.d(tag, "Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void Delete(final ResponseCallBack responseCallBack, int id) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseCallBack.onError(errorResponseHandler(error));
                    }
                }
        );
        RequestHandler.getInstance(context).addToRequestQueue(deleteRequest);
    }

    public String errorResponseHandler(VolleyError error) {
        String message = null;
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;
            JSONObject errors = null;
            if (response.data != null) {
                try {
                    String responseBody = new String(response.data);
                    errors = new JSONObject(responseBody);
                    message = errors.getString("Message");
                } catch (JSONException e) {
                    Log.d(context.getClass().getSimpleName(), "onErrorResponse: " + e.getMessage());
                }
            } else {
                message = "The server could not be found. Please try again after some time!!";
            }
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection Time Out! The server could not be found or no internet connection.";
        }

        return message;
    }

    public abstract void Save(final ResponseCallBack responseCallBack, T entity);
    public abstract T CreateNew();
    public abstract String GetUrl();
}