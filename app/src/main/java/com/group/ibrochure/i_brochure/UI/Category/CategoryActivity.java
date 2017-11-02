package com.group.ibrochure.i_brochure.UI.Category;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.Category.CategoryService;
import com.group.ibrochure.i_brochure.Infrastructure.CategoryAPI;
import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private CategoryService service;
    private CategoryAPI repository;
    private RequestQueue requestQueue;

    public CategoryActivity() {
        requestQueue = Volley.newRequestQueue(this);
        repository = new CategoryAPI(requestQueue, this);
        service = new CategoryService(repository);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        List<String> codes = new ArrayList<>(), names = new ArrayList<>();
        for (Category en : service.GetAll()){
            codes.add(en.getCode());
            names.add(en.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_single_choice, names
        );

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
