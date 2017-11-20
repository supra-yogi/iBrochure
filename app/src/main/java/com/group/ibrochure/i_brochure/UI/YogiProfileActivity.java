package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.group.ibrochure.i_brochure.R;

public class YogiProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yogi_profile);
    }

    public void onBackToHome(View view) {
        startActivity(new Intent(this, CreditActivity.class));
        finish();
    }
}
