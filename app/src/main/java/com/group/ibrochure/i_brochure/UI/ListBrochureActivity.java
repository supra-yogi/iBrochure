package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;
import com.srx.widget.PullToLoadView;

public class ListBrochureActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_brochure);
        session = new Session(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
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
            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.nav_mybrochure) {
            startActivity(new Intent(this, ListMyBrochureActivity.class));

        } else if (id == R.id.nav_credit) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
