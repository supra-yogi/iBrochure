package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.group.ibrochure.i_brochure.Domain.UserAccount.IUserAccountRepo;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yogi on 05/11/2017.
 */

public class UserAccountAPI extends BaseAPI<UserAccount> implements IUserAccountRepo {

    public UserAccountAPI(Context context) {
        super(context);
    }

    @Override
    public void Save(final ResponseCallBack responseCallBack, final UserAccount entity) {
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
                            responseCallBack.onError(error);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {         // Adding parameters
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Username", entity.getUsername());
                    params.put("Email", entity.getEmail());
                    params.put("Password", entity.getPassword());

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
                    params.put("Id", String.valueOf(entity.getId()));
                    params.put("Username", entity.getUsername());
                    params.put("Email", entity.getEmail());
                    params.put("Password", entity.getPassword());

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
    }

    @Override
    public UserAccount CreateNew() {
        return new UserAccount();
    }

    @Override
    public String GetUrl() {
        return URLs.URL_USERACCOUNT;
    }
}