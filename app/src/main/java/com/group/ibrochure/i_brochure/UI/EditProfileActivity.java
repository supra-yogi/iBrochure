package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Session session;
    private UserAccountAPI repository;
    private UserAccount userAccount;
    private final static int PICK_IMAGE = 100;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        repository = new UserAccountAPI(this);
        session = new Session(this);
        userAccount = repository.CreateNew();

        toolbar = (Toolbar) findViewById(R.id.edit_profile);
        setSupportActionBar(toolbar);

        //manipulate image
        imageView = (ImageView) findViewById(R.id.picture);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
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
                    String encodedImage = object.getString("Picture");
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    progressDialog.hide();

                    imageView.setImageBitmap(decodedByte);
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
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, session.getUserOrEmail());
    }

    public void onClose(View view) {
        startActivity(new Intent(this, ListBrochureActivity.class));
    }

    public void onSave(View view) {
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
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bb = baos.toByteArray();
        String image = Base64.encodeToString(bb, Base64.DEFAULT);

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
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
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
