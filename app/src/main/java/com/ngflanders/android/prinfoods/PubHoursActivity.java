package com.ngflanders.android.prinfoods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PubHoursActivity extends AppCompatActivity {

    private TextView dateTextView;
    private Switch dateSwitch;

    private String today_daymonthday, tom_daymonthday, tod_day, tom_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_hours);

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        Calendar c = Calendar.getInstance();
        final Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, 1);
        final Date tomorrow = c.getTime();

        DateFormat dmd = new SimpleDateFormat("EEEE, MMMM dd");
        today_daymonthday = dmd.format(today);
        tom_daymonthday = dmd.format(tomorrow);

        DateFormat d = new SimpleDateFormat("EEEE");
        tod_day = d.format(today);
        tom_day = d.format(tomorrow);

        dateTextView.setText(today_daymonthday);

        dateSwitch = (Switch) findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateTextView.setText(isChecked ? tom_daymonthday : today_daymonthday);
                updateTimes();
            }
        });
        updateTimes();

    }

    private void updateTimes() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PubTimes");
        query.whereEqualTo("dayOfWeek", !dateSwitch.isChecked() ? tod_day : tom_day);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {
                        switch (item.getString("meal")) {
                            case "Breakfast":
                                ((Button) findViewById(R.id.PubBreakfastButton)).setText("Breakfast\nGrill: ".concat(item.getString("timeGrill")
                                        + "\nShakes: ").concat(item.getString("timeShake")));
                                break;
                            case "Lunch":
                                ((Button) findViewById(R.id.PubLunchButton)).setText("Lunch\nGrill: ".concat(item.getString("timeGrill")
                                        + "\nShakes: ").concat(item.getString("timeShake")));
                                break;
                            case "Dinner":
                                ((Button) findViewById(R.id.PubDinnerButton)).setText("Dinner\nGrill: ".concat(item.getString("timeGrill")
                                        + "\nShakes: ").concat(item.getString("timeShake")));
                                break;
                        }
                    }
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

    }

}
