package com.group.ibrochure.i_brochure.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yogi on 07/11/2017.
 */

public class PaginateListBrochure {
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView rv;
    private BrochureAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    private final int size = 3;
    private ListBrochureAPI repository;
    private Session session;

    public PaginateListBrochure(Context context, PullToLoadView pullToLoadView) {
        this.context = context;
        this.pullToLoadView = pullToLoadView;
        session = new Session(context);

        repository = new ListBrochureAPI(context);
        //RECYCLER VIEW
        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new BrochureAdapter(context, new ArrayList<ListBrochure>());
        rv.setAdapter(adapter);

        initializePaginator();
    }

    /*
    PAGE DATA
     */
    public void initializePaginator() {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.initLoad();
        pullToLoadView.setPullCallback(new PullCallback() {

            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                loadData(nextPage);
            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                loadData(1);
            }

            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
    }

    /*
     LOAD MORE DATA
     SIMULATE USING HANDLERS
     */
    public void loadData(final int page) {
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                repository.GetListBrochureByPage(new ResponseCallBack() {
                    @Override
                    public void onResponse(JSONArray response) {}

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                ListBrochure entity = new ListBrochure();
                                entity.setId(jsonObject.getInt("Id"));
                                entity.setTitle(jsonObject.getString("Title"));
                                entity.setTelephone(jsonObject.getString("Telephone"));
                                entity.setPictureFront(jsonObject.getString("PictureFront"));
                                entity.setPictureBack(jsonObject.getString("PictureBack"));

                                UserAccount userAccount = new UserAccount();
                                userAccount.setName(jsonObject.getString("Username"));
//                                userAccount.setPicture(jsonObject.getString("Avatar"));

                                entity.setUserAccount(userAccount);
                                adapter.add(entity);
                            }

                            nextPage = page + 1;
                        } catch (JSONException e) {
                            Log.d("Error: ", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(context, "Response: " + error, Toast.LENGTH_LONG).show();
                    }
                }, page, size);

                //UPDATE PROPERTIES
                pullToLoadView.setComplete();
                isLoading = false;
            }
        }, 3000);
    }
}




