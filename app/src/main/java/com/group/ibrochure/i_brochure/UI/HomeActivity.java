package com.group.ibrochure.i_brochure.UI;

import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String[] friends = {
            "Aan Ardiyana",
            "Suprayogi Hermawan",
            "Claudio Antonius",
            "Julyanto Wijaya",
            "Ignatius Fernando",
            "Luthan Davin Cesario"
    };

    String[] desc = {
            "Ganteng",
            "Keren",
            "Gagah",
            "Gendut",
            "Kurus",
            "Good People"
    };

    ArrayList<Friend> arrayList = new ArrayList<>();
    RecyclerAdapter adapter = new RecyclerAdapter(arrayList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);

        Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/pacifico.ttf");
        mTitle.setTypeface(customFont);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        int count = 0;

        for (String friend : friends){
            arrayList.add(new Friend(friend, desc[count]));
            count++;
        }

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<Friend> friendArrayList = new ArrayList<>();
        for (Friend friend : arrayList) {
            String name = friend.getName().toLowerCase();
            if (name.contains(newText)) {
                friendArrayList.add(friend);
            } else {
                Toast.makeText(this, "Friend Not Found", Toast.LENGTH_SHORT).show();
            }
        }

        adapter.setFilter(friendArrayList);
        return true;
    }
}
