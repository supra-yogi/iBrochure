package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.R;

public class AanProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aan_profile);

        TextView tv = (TextView) findViewById(R.id.name_author1);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        tv.setTypeface(face);

    }

    public void onBackToHome(View view) {
        startActivity(new Intent(this, CreditActivity.class));
        finish();
    }
}
