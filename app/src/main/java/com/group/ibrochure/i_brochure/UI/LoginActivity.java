package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.egl.EGLDisplay;

public class LoginActivity extends AppCompatActivity {
    private UserAccountAPI repository;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = new UserAccountAPI(this);
        session = new Session(this);
    }

    public void onResume() {
        super.onResume();

        EditText userOrEmail = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        userOrEmail.setText("");
        password.setText("");
    }

    public void onLogin(View view) {
        EditText userOrEmail = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        if (userOrEmail.getText().toString().equals("")) {
            userOrEmail.setError("This field is required");
        } else if (password.getText().toString().equals("")) {
            password.setError("Password is required");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Please wait");
            repository.Login(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    progressDialog.hide();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            session.setId(jsonObject.getInt("Id"));
                            session.setUserOrEmail(jsonObject.getString("Username"));
                        }

                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } catch (JSONException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();

                }
            }, userOrEmail.getText().toString(), password.getText().toString());
        }
    }

    public void registerPage(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, ListBrochureActivity.class);
        startActivity(intent);
    }
}
