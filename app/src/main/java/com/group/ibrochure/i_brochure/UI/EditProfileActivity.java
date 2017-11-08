package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.group.ibrochure.i_brochure.Domain.Customer.Customer;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.CustomerAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Session session;
    private CustomerAPI repository;
    private UserAccountAPI userAccountRepository;
    private Customer customer;
    private UserAccount userAccount;
    private final static int PICK_IMAGE = 100;
    private ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        repository = new CustomerAPI(this);
        userAccountRepository = new UserAccountAPI(this);
        session = new Session(this);
        customer = repository.CreateNew();
        userAccount = userAccountRepository.CreateNew();

        toolbar = (Toolbar) findViewById(R.id.edit_profile);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.picture);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        userAccountRepository.GetByUsername(new ResponseCallBack() {
            EditText usernameText = (EditText) findViewById(R.id.username);
            EditText emailText = (EditText) findViewById(R.id.email);
            @Override
            public void onResponse(JSONArray response) {}

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    int id = object.getInt("Id");
                    String username = object.getString("Username");
                    String email = object.getString("Email");
                    session.setId(id);

                    usernameText.setText(username);
                    emailText.setText(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {}

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, session.getUserOrEmail());
    }

    public void onClose(View view) {
        startActivity(new Intent(this, ListBrochureActivity.class));
    }

    public void onPickPicture(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
