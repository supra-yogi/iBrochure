package com.group.ibrochure.i_brochure.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Infrastructure.CategoryAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private CategoryAPI repository;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        TAG = this.getClass().getSimpleName();
        //create new repository
        repository = new CategoryAPI(this);

        // Casts results into the ListView found within the main layout XML with id jsonData
        final ArrayAdapter<String> myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        ListView myList = (ListView) findViewById(R.id.listv);
        myList.setAdapter(myAdapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> myArrayList = new ArrayList<>();
                final ArrayList<Category> entities = new ArrayList<>();
                repository.GetAll(new ResponseCallBack() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Category category = new Category();
                                category.setId(Integer.parseInt(jsonObject.get("Id").toString()));
                                category.setCode(jsonObject.get("Code").toString());
                                category.setName(jsonObject.get("Name").toString());
                                entities.add(category);
                            }

//                            ArrayList<String> myArrayList = new ArrayList<>();
                            for (Category cat : entities) {
                                myArrayList.add(cat.getCode());
                            }

                            myAdapter.clear();
                            myAdapter.addAll(myArrayList);
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d("Error", e.getMessage());
                        }

                        Log.d("Response", response.toString());
                    }


                    @Override
                    public void onResponse(String response) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });


            }
        });

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.clear();
            }
        });
    }
}
