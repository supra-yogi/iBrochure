package com.group.ibrochure.i_brochure.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private UserAccountAPI repository;
    private Session session;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        repository = new UserAccountAPI(this);
        session = new Session(this);
        activity = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        repository.GetById(new ResponseCallBack() {
            TextView name = (TextView) findViewById(R.id.name);
            TextView username = (TextView) findViewById(R.id.username);
            TextView contact = (TextView) findViewById(R.id.contact);
            TextView address = (TextView) findViewById(R.id.address);
            TextView telephone = (TextView) findViewById(R.id.telephone);
            TextView email = (TextView) findViewById(R.id.email);

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        name.setText(jsonObject.getString("Name"));
                        username.setText(jsonObject.getString("Username"));
                        contact.setText(jsonObject.getString("Contact"));
                        address.setText(jsonObject.getString("Address"));
                        telephone.setText(jsonObject.getString("Telephone"));
                        email.setText(jsonObject.getString("Email"));
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
        }, session.getId());
    }

    public static Activity getInstance() {
        return activity;
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        Bundle bundle = getIntent().getExtras();
        if (bundle.getBoolean("fromListBrochure")){
            finish();
        } else {
            ListBrochureActivity.getInstance().finish();
            startActivity(new Intent(this, ListBrochureActivity.class));
        }
    }

    public void onLogout(View view) {
        session.logOut();
        ListBrochureActivity.getInstance().finish();
        startActivity(new Intent(this, ListBrochureActivity.class));
        finish();
    }
}
