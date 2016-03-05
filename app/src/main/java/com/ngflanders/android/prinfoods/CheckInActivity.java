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

        listView = (ListView) findViewById(R.id.checkin_list);
        SimpleAdapter adapter = new SimpleAdapter(this, friends_feed, R.layout.friend_item,
                new String[]{"name", "place", "time"},
                new int[]{R.id.friend_name, R.id.friend_loc, R.id.friend_time});
        listView.setAdapter(adapter);
        updateFriends();
    }


    @SuppressWarnings("unchecked")
    private void updateFriends() {
        friends_feed.clear();

        // gets Menu table
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CheckIn");

        // checks if object was last updated today
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
                    DateFormat df = new SimpleDateFormat("hh:mm a");
                    for (ParseObject item : objects) {
                        // add values to HashMap, then add Map to ArrayList
                        map = new HashMap<>();
                        map.put("name", item.getString("name"));
                        map.put("place", item.getString("place"));
                        map.put("time", df.format(item.getUpdatedAt()));
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

        // 12 hr : minutes am/pm
        final DateFormat df = new SimpleDateFormat("hh:mm a");

        // name of day, name of month  day
        final DateFormat dmd = new SimpleDateFormat("EEEE, MMMM d");

        // gets CheckIn table
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("CheckIn");

        // qualifies query, looking for profile object with same name
        query.whereEqualTo("name", curProf.getFirstName() + " " + curProf.getLastName());

        // gets CheckIn table
        final ParseObject parseObj = new ParseObject("CheckIn");

        // switch on button pressed
        switch (v.getId()) {
            case R.id.check_in_dining:

                // get first, and hopefully only matching profile in database
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        // if a matching profile was found, replace w/ new info
                        if (object != null) {
                            object.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            object.put("place", "Dining");
                            object.put("time", df.format(d));
                            object.put("date", dmd.format(d));
                            object.saveInBackground();
                        }
                        // else build new profile object to put into database
                        else {
                            parseObj.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            parseObj.put("place", "Dining");
                            parseObj.put("time", df.format(d));
                            parseObj.put("date", dmd.format(d));
                            parseObj.saveInBackground();
                        }
                    }
                });
                break;

            case R.id.check_in_pub:
                // get first, and hopefully only matching profile in database
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        // if a matching profile was found, replace w/ new info
                        if (object != null) {
                            object.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            object.put("place", "Pub");
                            object.put("time", df.format(d));
                            object.put("date", dmd.format(d));
                            object.saveInBackground();
                        }
                        // else build new profile object to put into database
                        else {
                            parseObj.put("name", curProf.getFirstName() + " " + curProf.getLastName());
                            parseObj.put("place", "Pub");
                            parseObj.put("time", df.format(d));
                            parseObj.put("date", dmd.format(d));
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
