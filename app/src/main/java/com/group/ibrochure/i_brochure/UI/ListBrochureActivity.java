package com.group.ibrochure.i_brochure.UI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.Infrastructure.UserAccountAPI;
import com.group.ibrochure.i_brochure.R;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListBrochureActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Session session;

    private String[] listitem;
    boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private UserAccountAPI userAccountRepository;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_brochure);
        session = new Session(this);
        userAccountRepository = new UserAccountAPI(this);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.toolbar_title);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/pacifico.ttf");
        tv.setTypeface(face);

        PullToLoadView pullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoadView);
        new PaginateListBrochure(this, pullToLoadView).initializePaginator();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listitem = getResources().getStringArray(R.array.shop_item);
        checkedItems = new boolean[listitem.length];

        if (session.getId() != 0) {
            userAccountRepository.GetById(new ResponseCallBack() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        UserAccount userAccount = new UserAccount();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            ImageView avatar = (ImageView) findViewById(R.id.avatar_sidebar);
                            TextView username = (TextView) findViewById(R.id.user_sidebar);
                            TextView email = (TextView) findViewById(R.id.email_sidebar);

                            Bitmap avatarBitmap = ConverterImage.decodeBase64(jsonObject.getString("Picture"));
                            avatar.setImageBitmap(avatarBitmap);
                            username.setText(jsonObject.getString("Name"));
                            email.setText(jsonObject.getString("Email"));
                        }
                    } catch (JSONException e) {
                        Log.d("Error", e.getMessage());
                    }
                }

                @Override
                public void onResponse(String response) {
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Response: " + error, Toast.LENGTH_LONG).show();
                }
            }, session.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_filter) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListBrochureActivity.this);
            mBuilder.setTitle("Brochure Category");
            mBuilder.setMultiChoiceItems(listitem, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                    if (isChecked) {
                        if (!mUserItems.contains(position)) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove(position);
                        }
                    }
                }
            });

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String item = "";
                    for (int i = 0; i < mUserItems.size(); i++) {
                        item = item + listitem[mUserItems.get(i)];
                        if (i != mUserItems.size() - 1) {
                            item = item + ", ";
                        }
                    }
                }
            });

            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    for (int i = 0; i < checkedItems.length; i++) {
                        checkedItems[i] = false;
                        mUserItems.clear();
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();

        }

        return true;
    }

    public static Activity getInstance() {
        return activity;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, ListBrochureActivity.class));
        } else if (id == R.id.nav_account) {
            if (session.getId() != 0) {
                Intent profile = new Intent(this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromListBrochure", true);
                profile.putExtras(bundle);
                startActivity(profile);
            } else
                startActivity(new Intent(this, FrontActivity.class));
        } else if (id == R.id.nav_mybrochure) {
            if (session.getId() != 0)
                startActivity(new Intent(this, ListMyBrochureActivity.class));
            else
                startActivity(new Intent(this, FrontActivity.class));
        } else if (id == R.id.nav_credit) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
