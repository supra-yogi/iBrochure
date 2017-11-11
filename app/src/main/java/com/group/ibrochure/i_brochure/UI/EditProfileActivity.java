package com.group.ibrochure.i_brochure.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {
    private Session session;
    private UserAccountAPI repository;
    private UserAccount userAccount;
    private final static int PICK_IMAGE = 100;
    private ImageView imageView;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        activity = this;

        repository = new UserAccountAPI(this);
        session = new Session(this);
        userAccount = repository.CreateNew();

        //manipulate image
        imageView = (ImageView) findViewById(R.id.picture);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        repository.GetByUsername(new ResponseCallBack() {
            EditText username = (EditText) findViewById(R.id.username);
            EditText email = (EditText) findViewById(R.id.email);
            EditText name = (EditText) findViewById(R.id.name);
            EditText contact = (EditText) findViewById(R.id.contact);
            EditText telephone = (EditText) findViewById(R.id.telephone);
            EditText address = (EditText) findViewById(R.id.address);

            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    session.setId(object.getInt("Id"));
                    Bitmap decodedByte = ConverterImage.decodeBase64(object.getString("Picture"));

                    if (!decodedByte.equals("")) {
                        imageView.setImageBitmap(decodedByte);
                    }
                    username.setText(object.getString("Username"));
                    email.setText(object.getString("Email"));
                    name.setText(object.getString("Name"));
                    contact.setText(object.getString("Contact"));
                    telephone.setText(object.getString("Telephone"));
                    address.setText(object.getString("Address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        }, session.getUserOrEmail());
    }

    public void onClose(View view) {
        finish();
    }

    public void onSave(View view) {
        ProfileActivity.getInstance().finish();

        EditText name = (EditText) findViewById(R.id.name);
        EditText contact = (EditText) findViewById(R.id.contact);
        EditText telephone = (EditText) findViewById(R.id.telephone);
        EditText address = (EditText) findViewById(R.id.address);
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);

        userAccount.setId(session.getId());
        userAccount.setUsername(username.getText().toString());
        userAccount.setEmail(email.getText().toString());
        userAccount.setName(name.getText().toString());
        userAccount.setContact(contact.getText().toString());
        userAccount.setTelephone(telephone.getText().toString());
        userAccount.setAddress(address.getText().toString());

        //Convert image
        String image = ConverterImage.encodeBase64(imageView);
        userAccount.setPicture(image);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        repository.Save(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromListBrochure", false);
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                profile.putExtras(bundle);
                startActivity(profile);
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        }, userAccount);
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
