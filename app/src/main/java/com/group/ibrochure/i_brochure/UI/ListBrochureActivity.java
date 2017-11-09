package com.group.ibrochure.i_brochure.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
        new PaginateBrochure(this, pullToLoadView).initializePaginator();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostBrochureActivity.class));
            }
        });

        if (session.getId() != 0) {
            fab.show();
        } else {
            fab.hide();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_brochure:
                if (session.getId() != 0) {
                    startActivity(new Intent(getBaseContext(), ProfileActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), FrontActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
