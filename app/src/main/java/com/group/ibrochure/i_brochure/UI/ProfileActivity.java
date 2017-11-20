package com.group.ibrochure.i_brochure.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        repository.GetById(new ResponseCallBack() {
            TextView name = (TextView) findViewById(R.id.name);
            TextView username = (TextView) findViewById(R.id.username);
            TextView contact = (TextView) findViewById(R.id.contact);
            TextView address = (TextView) findViewById(R.id.address);
            TextView telephone = (TextView) findViewById(R.id.telephone);
            TextView email = (TextView) findViewById(R.id.email);
            CircleImageView circleImageView = (CircleImageView) findViewById(R.id.avatar_profile);

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

                        String img = jsonObject.getString("Picture");
                        if (!img.equals("")) {
                            Bitmap bitmap = ConverterImage.decodeBase64(img);
                            circleImageView.setImageBitmap(bitmap);
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
        }, session.getId());
    }

    public static Activity getInstance() {
        return activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_profile:
                startActivity(new Intent(this, EditProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
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
