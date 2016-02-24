package com.ngflanders.android.prinfoods;

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

public class CheckInActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        friends = new ArrayList<>();

        listView = (ListView) findViewById(R.id.checkin_list);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.friend_item, friends));
        updateFriends();
    }


    @SuppressWarnings("unchecked")
    private void updateFriends() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CheckIn"); // gets Menu table


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {
                        String s = item.getString("name");
                        friends.add(s);
                    }

                    ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }
}
