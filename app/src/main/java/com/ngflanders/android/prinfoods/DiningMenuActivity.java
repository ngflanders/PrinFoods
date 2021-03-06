package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DiningMenuActivity extends AppCompatActivity {

    static final String EXTRA_MEAL = "com.example.EXTRA_MEAL";
    static final String EXTRA_DATE = "com.example.EXTRA_DATE";

    private ListView listView;
    private ArrayList<String> menu;
    private String meal, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_menu);

        menu = new ArrayList<>();

        listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.menu_item, menu));

        Intent intent = getIntent();
        meal = intent.getStringExtra(EXTRA_MEAL);
        date = intent.getStringExtra(EXTRA_DATE);
        updateMenu();
    }

    @SuppressWarnings("unchecked")
    private void updateMenu() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu"); // gets Menu table

        // qualifies query for date and meal type
        query.whereEqualTo("date", date);
        query.whereEqualTo("meal", meal);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {
                        menu.add(item.getString("dish"));
                    }
                    ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
}