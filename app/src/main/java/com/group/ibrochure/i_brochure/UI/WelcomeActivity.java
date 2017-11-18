package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tv = (TextView) findViewById(R.id.title_greeting);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        tv.setTypeface(face);
    }

    public void getStarted(View view) {
        startActivity(new Intent(this, ListBrochureActivity.class));
        finish();
    }
}
