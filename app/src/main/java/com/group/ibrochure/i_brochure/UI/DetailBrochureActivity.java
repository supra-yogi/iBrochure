package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Binder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailBrochureActivity extends AppCompatActivity {
    private Session session;
    private ListBrochureAPI repository;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_brochure);
        session = new Session(this);
        repository = new ListBrochureAPI(this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        repository.GetById(new ResponseCallBack() {
            TextView title = (TextView) findViewById(R.id.title);
            TextView telephone = (TextView) findViewById(R.id.telephone);
            TextView description = (TextView) findViewById(R.id.description);
            TextView user = (TextView) findViewById(R.id.user);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            LinearLayout sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<Bitmap> images = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        title.setText(jsonObject.getString("Title"));
                        telephone.setText(jsonObject.getString("Telephone"));
                        description.setText(jsonObject.getString("Description"));
                        user.setText(jsonObject.getString("Username"));
                        String pictureFrontByte = jsonObject.getString("PictureFront");
                        String pictureBackByte = jsonObject.getString("PictureBack");
                        if (!pictureFrontByte.equals("")) {
                            Bitmap pictureFront = ConverterImage.decodeBase64(pictureFrontByte);
                            images.add(pictureFront);
                            pictureFront = null;
                            System.gc();
                        }

                        if (!pictureBackByte.equals("")) {
                            Bitmap pictureBack = ConverterImage.decodeBase64(pictureBackByte);
                            images.add(pictureBack);
                            pictureBack = null;
                            System.gc();
                        }
                    }

                    if (images.size() != 0) {
                        ViewPagerAdapterSlider viewPagerAdapter = new ViewPagerAdapterSlider(getApplicationContext(), images);
                        viewPager.setAdapter(viewPagerAdapter);
                        final int dotscount = viewPagerAdapter.getCount();
                        if (sliderDotspanel.getChildCount() != dotscount) {
                            final ImageView[] dots = new ImageView[dotscount];
                            for (int i = 0; i < dotscount; i++) {
                                dots[i] = new ImageView(getApplicationContext());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                params.setMargins(8, 0, 8, 0);

                                sliderDotspanel.addView(dots[i], params);
                            }

                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    for (int i = 0; i < dotscount; i++) {
                                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                                    }

                                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });
                        }
                    }

                    progressDialog.hide();
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onResponse(String response) {}

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        }, id);
    }

    public void onBackClick (View view) {
        finish();
    }
}
