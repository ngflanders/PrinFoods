package com.ngflanders.android.prinfoods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DiningMenuActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_menu);

        menu = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu_item, menu);

        listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);

        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        updateMenu();


    }

    private void updateMenu() {

        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                if (e == null) {
                    objects.clear();
                    for (ParseObject item : objects) {
                        String s = item.getString("dish");
                        menu.add(s);
                    }
                    ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }

}
