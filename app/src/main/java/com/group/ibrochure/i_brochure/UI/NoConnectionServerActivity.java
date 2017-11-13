package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.group.ibrochure.i_brochure.R;

public class NoConnectionServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connectionz_server);

    }

    public void onReload(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
