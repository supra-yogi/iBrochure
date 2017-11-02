package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.R;
import com.group.ibrochure.i_brochure.UI.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transition);
        tv = (TextView) findViewById(R.id.brand);
        tv.startAnimation(anim);

        final Intent i = new Intent(this, LoginActivity.class);

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
