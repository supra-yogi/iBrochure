package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.R;

public class EditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = (Toolbar) findViewById(R.id.edit_profile);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    public void onClose(View view) {
        startActivity(new Intent(this, ListBrochureActivity.class));
    }
}
