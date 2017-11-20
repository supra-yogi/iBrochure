package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.R;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        TextView designer = (TextView) findViewById(R.id.designer);
        TextView programmer = (TextView) findViewById(R.id.programmer);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        designer.setTypeface(face);
        programmer.setTypeface(face);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_credit);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void onGoToAan(View view) {
        startActivity(new Intent(this, AanProfileActivity.class));
    }

    public void onGoToYogi(View view) {
        startActivity(new Intent(this, YogiProfileActivity.class));
    }

    public void onBackToHome(View view) {
        startActivity(new Intent(this, ListBrochureActivity.class));
    }
}
