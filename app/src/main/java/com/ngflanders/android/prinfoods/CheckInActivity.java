package com.ngflanders.android.prinfoods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.facebook.Profile;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CheckInActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> friends_feed;
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        friends_feed = new ArrayList<>();
        map = new HashMap<>();

        listView = (ListView) findViewById(R.id.checkin_list);
        SimpleAdapter adapter = new SimpleAdapter(this, friends_feed, R.layout.friend_item,
                new String[]{"name", "time", "place"},
                new int[]{R.id.friend_name, R.id.friend_time, R.id.friend_loc});
        listView.setAdapter(adapter);
        updateFriends();
    }


    @SuppressWarnings("unchecked")
    private void updateFriends() {
        friends_feed.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CheckIn"); // gets Menu table

        // TODO replace deprecated methods
        Date midnight = new Date();
        midnight.setHours(0);
        midnight.setMinutes(0);
        midnight.setSeconds(0);

        Date elevenfiftynine = new Date();
        elevenfiftynine.setHours(23);
        elevenfiftynine.setMinutes(59);
        elevenfiftynine.setSeconds(59);

        query.whereGreaterThan("updatedAt", midnight);
        query.whereLessThan("updatedAt", elevenfiftynine);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    String s;
                    Date d;
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    for (ParseObject item : objects) {
                        map = new HashMap<>();

                        s = item.getString("name");
                        map.put("name", s);

                        s = item.getString("place");
                        map.put("place", s);

                        d = item.getUpdatedAt();
                        s = df.format(d);
                        map.put("time", s);

                        friends_feed.add(map);
                    }

                    ((SimpleAdapter) listView.getAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }


    public void onButtonClick(View v) {
        final Profile curProf = Profile.getCurrentProfile();

        final Date d = new Date();
        final DateFormat df = new SimpleDateFormat("hh:mm a");


        final ParseQuery<ParseObject> query = ParseQuery.getQuery("CheckIn"); // gets Menu table

        query.whereEqualTo("name", curProf.getFirstName() + " " + curProf.getLastName());

        final ParseObject parseObj = new ParseObject("CheckIn");


        // TODO find out how what data iOS needs, so we can upload that too

        switch (v.getId()) {
            case R.id.check_in_dining:

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object != null) {

                            object.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            object.put("place", "Dining");
                            object.put("time", df.format(d));
                            object.saveInBackground();
                        } else {
                            parseObj.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            parseObj.put("place", "Dining");
                            parseObj.put("time", df.format(d));
                            parseObj.saveInBackground();
                        }
                    }
                });
                break;

            case R.id.check_in_pub:

                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null) {
                            object.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            object.put("place", "Pub");
                            object.put("time", df.format(d));
                            object.saveInBackground();
                        } else {
                            parseObj.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            parseObj.put("place", "Pub");
                            parseObj.put("time", df.format(d));
                            parseObj.saveInBackground();
                        }
                    }
                });
                break;

            default:
                throw new RuntimeException("Unknown button ID");

        }


        updateFriends();

    }

}
