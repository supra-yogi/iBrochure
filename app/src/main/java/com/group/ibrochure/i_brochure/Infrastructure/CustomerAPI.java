package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.group.ibrochure.i_brochure.Domain.Customer.Customer;
import com.group.ibrochure.i_brochure.Domain.Customer.ICustomerRepo;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.IListBrochureRepo;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 08/11/2017.
 */

public class CustomerAPI extends BaseAPI<Customer> implements ICustomerRepo {

    private Session session;

    public CustomerAPI(Context context) {
        super(context);
        session = new Session(context);
    }

    @Override
    public void Save(final ResponseCallBack responseCallBack, final Customer entity) {
        if (entity.getId() == 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                            if (responseBody != null && error.networkResponse != null)
                                try {
                                    errors = new JSONObject(responseBody);
                                    String message = errors.getString("Message");
                                    responseCallBack.onError(message);
                                } catch (JSONException e) {
                                    Log.d(context.getClass().getSimpleName(), "onErrorResponse: " + e.getMessage());
                                }
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Name", entity.getName());
                    params.put("Contact", entity.getContact());
                    params.put("Telp", entity.getTelp());
                    params.put("Address", entity.getAddress());
                    params.put("Picture", entity.getPicture());
                    params.put("UseraccountId", String.valueOf(session.getId()));

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(postRequest);
        } else {
            StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            responseCallBack.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseCallBack.onError(error);
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Name", entity.getName());
                    params.put("Contact", entity.getContact());
                    params.put("Telp", entity.getTelp());
                    params.put("Address", entity.getAddress());
                    params.put("Picture", entity.getPicture());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
    }

    @Override
    public Customer CreateNew() {
        return new Customer();
    }

    @Override
    public String GetUrl() {
        return URLs.URL_CUSTOMER;
    }
}
