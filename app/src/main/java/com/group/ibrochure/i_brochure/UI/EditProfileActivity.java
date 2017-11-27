package com.group.ibrochure.i_brochure.UI;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private Session session;
    private UserAccountAPI repository;
    private UserAccount userAccount;
    private final static int PICK_IMAGE = 100;
    private final static int REQUEST_CAMERA = 200;
    private CircleImageView imageView;
    private static Activity activity;
    private EditText username;
    private EditText email;
    private EditText name;
    private EditText contact;
    private EditText telephone;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        activity = this;

        repository = new UserAccountAPI(this);
        session = new Session(this);
        userAccount = repository.CreateNew();

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        contact = (EditText) findViewById(R.id.contact);
        telephone = (EditText) findViewById(R.id.telephone);
        address = (EditText) findViewById(R.id.address);

        //manipulate image
        imageView = (CircleImageView) findViewById(R.id.picture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        repository.GetByUsername(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    session.setId(object.getInt("Id"));

                    String picture = object.getString("Picture");
                    if (!picture.equals("")) {
                        Bitmap decodedByte = ConverterImage.decodeBase64(picture);
                        imageView.setImageBitmap(decodedByte);
                        decodedByte = null;
                        System.gc();
                    }
                    username.setText(object.getString("Username"));
                    email.setText(object.getString("Email"));
                    name.setText(object.getString("Name"));
                    contact.setText(object.getString("Contact"));
                    telephone.setText(object.getString("Telephone"));
                    address.setText(object.getString("Address"));

                    progressDialog.hide();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save_profile:
                ProfileActivity.getInstance().finish();

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
                image = null;
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }


//    public void onPickPicture(View view) {
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
//    }


    public void selectImage(View view) {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                   if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                       ActivityCompat.requestPermissions(EditProfileActivity.this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
//                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                       startActivityForResult(intent, REQUEST_CAMERA);
                   } else {
                       afterPermission();
                   }
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, PICK_IMAGE);
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public void afterPermission() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                afterPermission();
            } else {
                Toast.makeText(this, "Unable to get permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        } else if (requestCode == REQUEST_CAMERA) {
            Bundle bundle = data.getExtras();
            assert bundle != null;
            final Bitmap bmp = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bmp);
        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        Bundle bundle = getIntent().getExtras();

        if (name.getText().toString().equals("")) {
            name.setError("Name is required");
        } else if (contact.getText().toString().equals("")) {
            contact.setError("Contact is required");
        } else if (telephone.getText().toString().equals("")) {
            telephone.setError("Telephone is required");
        } else {
            if (bundle != null) {
                if (bundle.getBoolean("fromRegister")) {
                    ListBrochureActivity.getInstance().finish();
                    startActivity(new Intent(this, ListBrochureActivity.class));
                }
            }

            finish();
        }
    }



}
