package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.group.ibrochure.i_brochure.Infrastructure.URLs;
import com.group.ibrochure.i_brochure.R;

import java.net.HttpURLConnection;
import java.net.URL;

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

        final Intent login = new Intent(this, PostActivity.class);
        final Intent noConnection = new Intent(this, NoConnectionServerActivity.class);
        //Check Connection
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isConnected()) {
                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                startActivity(login);
                                finish();
                            }
                        }
                    };
                    timer.start();
                } else {
                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                startActivity(noConnection);
                                finish();
                            }
                        }
                    };
                    timer.start();
                }
            }
        });
        connection.start();
    }

    public boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                // Network is available but check if we can get access from the url.
                URL url = new URL(URLs.URL_SERVER);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); // Timeout 3 seconds.
                urlc.connect();

                if (urlc.getResponseCode() == 200) { // Successful response.
                    return true;
                } else {
//                    Toast.makeText(this, "Url is down", Toast.LENGTH_SHORT).show();
                    Log.d("NO INTERNET", "NO INTERNET");
                    return false;
                }
            } else {
//                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("No Connection", "No Internet Connection");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
