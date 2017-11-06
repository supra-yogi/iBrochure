package com.group.ibrochure.i_brochure.UI;

import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<ListBrochure> arrayList;
    private RecycleAdapter adapter;
    private ListBrochureAPI repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrayList = new ArrayList<>();
        adapter = new RecycleAdapter(arrayList);
        repository = new ListBrochureAPI(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);

        Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/pacifico.ttf");
        mTitle.setTypeface(customFont);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final ArrayList<ListBrochure> entities = new ArrayList<>();
        repository.GetAll(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        ListBrochure entity = new ListBrochure();
                        entity.setId(Integer.parseInt(jsonObject.get("Id").toString()));
                        entity.setTitle(jsonObject.get("Title").toString());
                        entity.setDescription(jsonObject.get("Description").toString());
                        entities.add(entity);
                    }
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }

                for (ListBrochure entity : entities) {
                    arrayList.add(entity);
                }

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(VolleyError volleyError) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<ListBrochure> friendArrayList = new ArrayList<>();
        for (ListBrochure entity : arrayList) {
            String name = entity.getTitle().toLowerCase();
            if (name.contains(newText)) {
                friendArrayList.add(entity);
            }
        }

        adapter.setFilter(friendArrayList);
        return true;
    }
}
