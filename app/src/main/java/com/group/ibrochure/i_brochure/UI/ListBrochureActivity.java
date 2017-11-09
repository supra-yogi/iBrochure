package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Infrastructure.Session;
import com.group.ibrochure.i_brochure.R;
import com.srx.widget.PullToLoadView;

public class ListBrochureActivity extends AppCompatActivity {
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



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_brochure:
                if (session.getId() != 0) {
                    startActivity(new Intent(getBaseContext(), ListMyBrochureActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), FrontActivity.class));
                }
                break;
            case R.id.action_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.action_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.action_logout:
                session.logOut();
                finish();
                System.exit(0);
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
