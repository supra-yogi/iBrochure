package com.group.ibrochure.i_brochure.UI;

import android.app.ProgressDialog;
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
    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repository = new UserAccountAPI(this);
    }

    public void onClick(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        UserAccount userAccount = repository.CreateNew();
        userAccount.setUsername(username.getText().toString());
        userAccount.setEmail(email.getText().toString());
        userAccount.setPassword(password.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.show();
        repository.Save(new ResponseCallBack() {
            @Override
            public void onResponse(JSONArray response) {
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, userAccount);
    }
}
