package com.group.ibrochure.i_brochure.Infrastructure;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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
    public void Save(final ResponseCallBack responseCallBack, final ListBrochure entity) {
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
                            String msg = null;
                            if (error instanceof NetworkError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                msg = "The server could not be found. Please try again after some time!!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                msg = "Parsing error! Please try again after some time!!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof NoConnectionError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof TimeoutError) {
                                msg = "Connection TimeOut! Please check your internet connection.";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else {
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
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Title", entity.getTitle());
                    params.put("Telephone", entity.getTelephone());
                    params.put("Address", entity.getAddress());
                    params.put("PostingDate", entity.getPostingDate());
                    params.put("Description", entity.getDescription());
                    params.put("UseraccountId", String.valueOf(entity.getUserAccount().getId()));
                    params.put("PictureFront", entity.getPictureFront());
                    params.put("PictureBack", entity.getPictureBack());
                    params.put("CategoryId", String.valueOf(entity.getCategory().getId()));

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
                            String msg = null;
                            if (error instanceof NetworkError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                msg = "The server could not be found. Please try again after some time!!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                msg = "Parsing error! Please try again after some time!!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof NoConnectionError) {
                                msg = "Cannot connect to Internet...Please check your connection!";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else if (error instanceof TimeoutError) {
                                msg = "Connection TimeOut! Please check your internet connection.";
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            } else {
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
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Id", String.valueOf(entity.getId()));
                    params.put("Title", entity.getTitle());
                    params.put("Telephone", entity.getTelephone());
                    params.put("Address", entity.getAddress());
                    params.put("Description", entity.getDescription());
                    params.put("PictureFront", entity.getPictureFront());
                    params.put("PictureBack", entity.getPictureBack());
                    params.put("CategoryId", String.valueOf(entity.getCategory().getId()));

                    return params;
                }
            };
            RequestHandler.getInstance(context).addToRequestQueue(putRequest);
        }
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
                        String msg = null;
                        if (error instanceof NetworkError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            msg = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            msg = "Parsing error! Please try again after some time!!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            msg = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else {
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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
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
                        String msg = null;
                        if (error instanceof NetworkError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            msg = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            msg = "Parsing error! Please try again after some time!!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            msg = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            msg = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        } else {
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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UseraccountId", String.valueOf(userAccount.getId()));
                params.put("Page", String.valueOf(page));
                params.put("Size", String.valueOf(size));

                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(postRequest);
    }
}
