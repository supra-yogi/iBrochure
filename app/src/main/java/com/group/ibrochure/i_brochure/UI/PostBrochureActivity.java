package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.CategoryAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PostBrochureActivity extends AppCompatActivity {

    private Session session;
    private CategoryAPI categoryRepository;
    private ListBrochureAPI repository;
    private HashMap<Integer, Integer> spinnerMap;
    private Spinner spinner;
    private ImageView pictureFront;
    private ImageView pictureBack;
    private final static int PICK_IMAGE_FRONT = 100;
    private final static int PICK_IMAGE_BACK = 200;
    private EditText title;
    private EditText address;
    private EditText description;
    private EditText telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_brochure);
        session = new Session(this);
        categoryRepository = new CategoryAPI(this);
        repository = new ListBrochureAPI(this);

        pictureFront = (ImageView) findViewById(R.id.pictureFront);
        pictureBack = (ImageView) findViewById(R.id.pictureBack);
        title = (EditText) findViewById(R.id.brochure_title_post);
        address = (EditText) findViewById(R.id.brochure_address_post);
        description = (EditText) findViewById(R.id.brochure_desc_post);
        telephone = (EditText) findViewById(R.id.brochure_telp_post);

        spinnerMap = new HashMap<>();
        spinner = (Spinner) findViewById(R.id.brochure_category_post);
        categoryRepository.GetAll(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String[] categories = new String[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        spinnerMap.put(i, jsonObject.getInt("Id"));
                        categories[i] = jsonObject.getString("Name");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.d("Error", e.getMessage());
                }

            }

            @Override
            public void onResponse(String response) {
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClose(View view) {
        finish();
    }

    public void onSave(View view) {
        if (title.getText().toString().equals("")) {
            title.setError("Title is required");
        } else if (telephone.getText().toString().equals("")) {
            telephone.setError("Telephone is required");
        } else if (address.getText().toString().equals("")) {
            address.setError("Address is required");
        } else {
            ListBrochure listBrochure = new ListBrochure();

//        String CategoryName = spinner.getSelectedItem().toString();
            int CategoryId = spinnerMap.get(spinner.getSelectedItemPosition());
            Category category = new Category();
            category.setId(CategoryId);
            UserAccount userAccount = new UserAccount();
            userAccount.setId(session.getId());

            String encodePictureFront = ConverterImage.encodeBase64(pictureFront);
            String encodePictureBack = ConverterImage.encodeBase64(pictureBack);

            listBrochure.setTitle(title.getText().toString());
            listBrochure.setAddress(address.getText().toString());
            listBrochure.setTelephone(telephone.getText().toString());
            listBrochure.setDescription(description.getText().toString());
            listBrochure.setCategory(category);
            listBrochure.setUserAccount(userAccount);
            listBrochure.setPictureFront(encodePictureFront);
            listBrochure.setPictureBack(encodePictureBack);

            // Parse the input date
            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String DateToStr = format.format(curDate);
            listBrochure.setPostingDate(DateToStr);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            //Disable touch
            repository.Save(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    progressDialog.hide();
                    ListMyBrochureActivity.getInstance().finish();
                    Intent myBrochure = new Intent(getApplicationContext(), ListMyBrochureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isUpdated", true);
                    myBrochure.putExtras(bundle);
                    startActivity(myBrochure);
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                }
            }, listBrochure);
        }
    }

    public void onPickPictureFront(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_FRONT);
    }

    public void onPickPictureBack(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_BACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FRONT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pictureFront.setImageURI(imageUri);
        } else if (requestCode == PICK_IMAGE_BACK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pictureBack.setImageURI(imageUri);
        }
    }
}
