package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.group.ibrochure.i_brochure.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transition);
        imgV = (ImageView) findViewById(R.id.brand);
        imgV.startAnimation(anim);

        final Intent i = new Intent(this, RegisterActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
