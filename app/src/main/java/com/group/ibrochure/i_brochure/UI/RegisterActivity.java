package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;

public class RegisterActivity extends AppCompatActivity {
    private UserAccountAPI repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repository = new UserAccountAPI(this);
    }

    public void onRegister(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        if (username.getText().toString().equals("")) {
            username.setError("Username is required");
        } else if (email.getText().toString().equals("")) {
            email.setError("Email is required");
        } else if (password.getText().toString().equals("")){
            password.setError("Password is required");
        } else {
            UserAccount userAccount = repository.CreateNew();
            userAccount.setUsername(username.getText().toString());
            userAccount.setEmail(email.getText().toString());
            userAccount.setPassword(password.getText().toString());

            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.show();
            progressDialog.setMessage("Please wait");
            repository.Save(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                }

                @Override
                public void onResponse(String response) {
                    startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                    progressDialog.hide();
                }

                @Override
                public void onError(VolleyError volleyError) {
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            }, userAccount);
        }
    }

    public void onResume() {
        super.onResume();

        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        username.setText("");
        email.setText("");
        password.setText("");
    }
}
