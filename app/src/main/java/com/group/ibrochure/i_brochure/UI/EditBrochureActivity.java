package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.io.StringReader;
import java.util.Calendar;
import java.util.HashMap;

public class EditBrochureActivity extends AppCompatActivity {

    private Session session;
    private CategoryAPI categoryRepository;
    private ListBrochureAPI repository;
    private HashMap<Integer, Integer> spinnerMap;
    private Spinner spinner;
    private ImageView pictureFront;
    private ImageView pictureBack;
    private final static int PICK_IMAGE_FRONT = 100;
    private final static int PICK_IMAGE_BACK = 200;
    private int id;
    private EditText title;
    private EditText address;
    private EditText description;
    private EditText telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_brochure);

        session = new Session(this);
        categoryRepository = new CategoryAPI(this);
        repository = new ListBrochureAPI(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Id");

        pictureFront = (ImageView) findViewById(R.id.pictureFront);
        pictureBack = (ImageView) findViewById(R.id.pictureBack);
        title = (EditText) findViewById(R.id.brochure_title_post);
        address = (EditText) findViewById(R.id.brochure_address_post);
        description = (EditText) findViewById(R.id.brochure_desc_post);
        telephone = (EditText) findViewById(R.id.brochure_telp_post);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        repository.GetById(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.hide();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        title.setText(jsonObject.getString("Title"));
                        address.setText(jsonObject.getString("Address"));
                        telephone.setText(jsonObject.getString("Telephone"));
                        description.setText(jsonObject.getString("Description"));
                        String imageFrontByte = jsonObject.getString("PictureFront");
                        String imageBackByte = jsonObject.getString("PictureBack");

                        if (!imageFrontByte.equals("")) {
                            Bitmap pictureFrontDecode = ConverterImage.decodeBase64(imageFrontByte);
                            pictureFront.setImageBitmap(pictureFrontDecode);
                            pictureFrontDecode = null;
                            System.gc();
                        }

                        if (!imageBackByte.equals("")) {
                            Bitmap pictureBackDecode = ConverterImage.decodeBase64(imageBackByte);
                            pictureBack.setImageBitmap(pictureBackDecode);
                            pictureBackDecode = null;
                            System.gc();
                        }
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

        spinnerMap = new HashMap<>();
        spinner = (Spinner) findViewById(R.id.brochure_category_post);
        categoryRepository.GetAll(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String[] categories = new String[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        int categoryIdAPI = jsonObject.getInt("Id");
                        spinnerMap.put(i, categoryIdAPI);
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
        ListBrochure listBrochure = new ListBrochure();

        int CategoryId = spinnerMap.get(spinner.getSelectedItemPosition());
        Category category = new Category();
        category.setId(CategoryId);

        String encodePictureFront = ConverterImage.encodeBase64(pictureFront);
        String encodePictureBack = ConverterImage.encodeBase64(pictureBack);

        listBrochure.setId(id);
        listBrochure.setTitle(title.getText().toString());
        listBrochure.setAddress(address.getText().toString());
        listBrochure.setTelephone(telephone.getText().toString());
        listBrochure.setDescription(description.getText().toString());
        listBrochure.setCategory(category);
        listBrochure.setPictureFront(encodePictureFront);
        listBrochure.setPictureBack(encodePictureBack);

        encodePictureBack = null;
        encodePictureFront = null;

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
                Intent detailBrochure = new Intent(getApplicationContext(), DetailBrochureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", id);
                detailBrochure.putExtras(bundle);
                startActivity(detailBrochure);
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        }, listBrochure);
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
        } else if (requestCode == PICK_IMAGE_BACK && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            pictureBack.setImageURI(imageUri);
        }
    }
}
