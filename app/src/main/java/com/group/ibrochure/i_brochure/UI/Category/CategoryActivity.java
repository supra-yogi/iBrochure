package com.group.ibrochure.i_brochure.UI.Category;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.group.ibrochure.i_brochure.Domain.Category.CategoryService;
import com.group.ibrochure.i_brochure.R;

public class CategoryActivity extends AppCompatActivity {
    private CategoryService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

//        service = new CategoryService();
    }
}
