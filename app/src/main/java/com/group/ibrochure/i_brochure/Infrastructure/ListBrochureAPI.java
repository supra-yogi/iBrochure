package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.IListBrochureRepo;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 06/11/2017.
 */

public class ListBrochureAPI extends BaseAPI<ListBrochure> implements IListBrochureRepo {

    public ListBrochureAPI(Context context) {
        super(context);
    }

    @Override
    public void Save(ResponseCallBack responseCallBack, ListBrochure entity) {

    }

    @Override
    public ListBrochure CreateNew() {
        return new ListBrochure();
    }

    @Override
    public String GetUrl() {
        return URLs.URL_LISTBROCHURE;
    }

    @Override
    public void GetListBrochureByPage(final ResponseCallBack responseCallBack, final int page, final int size) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getListBrochureByPage/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String responseBody = new String(error.networkResponse.data);
                        JSONObject errors = null;
                        if (responseBody != null && error.networkResponse != null) {
                            try {
                                errors = new JSONObject(responseBody);
                                String message = errors.getString("Message");
                                responseCallBack.onError(message);
                            } catch (JSONException e) {
                                Log.d(context.getClass().getSimpleName(), "onErrorResponse: " + e.getMessage());
                            }
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Page", String.valueOf(page));
                params.put("Size", String.valueOf(size));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }

    @Override
    public void GetListMyBrochureByPage(final ResponseCallBack responseCallBack, final UserAccount userAccount, final int page, final int size) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "getAllByUseraccountByPage/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseCallBack.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String responseBody = new String(error.networkResponse.data);
                        JSONObject errors = null;
                        if (responseBody != null && error.networkResponse != null) {
                            try {
                                errors = new JSONObject(responseBody);
                                String message = errors.getString("Message");
                                responseCallBack.onError(message);
                            } catch (JSONException e) {
                                Log.d(context.getClass().getSimpleName(), "onErrorResponse: " + e.getMessage());
                            }
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UseraccountId", String.valueOf(userAccount.getId()));
                params.put("Page", String.valueOf(page));
                params.put("Size", String.valueOf(size));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }
}
