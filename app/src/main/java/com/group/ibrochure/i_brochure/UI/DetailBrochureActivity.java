package com.group.ibrochure.i_brochure.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        id = Integer.parseInt(getIntent().getStringExtra("BrochureId"));
        repository.GetById(new ResponseCallBack() {
//            TextView textViewId = (TextView) findViewById(R.id.brochureId);
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Title"), Toast.LENGTH_SHORT).show();

//                        textViewId.setText(jsonObject.getString("Title"));
                    }
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
}
