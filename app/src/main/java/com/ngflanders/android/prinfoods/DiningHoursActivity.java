package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class DiningHoursActivity extends AppCompatActivity {

    static final String EXTRA_DATE = "com.example.EXTRA_DATE";
    static final String EXTRA_MEAL = "com.example.EXTRA_MEAL";
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;

    private TextView dateTextView;
    private Switch dateSwitch;

    String now_dmd, tom_dmd, tod_d, tom_d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hours);

        breakfastButton = (Button) findViewById(R.id.BreakfastButton);
        lunchButton = (Button) findViewById(R.id.LunchButton);
        dinnerButton = (Button) findViewById(R.id.DinnerButton);

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        Calendar c = Calendar.getInstance();
        final Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, 1);
        final Date tomorrow = c.getTime();

        DateFormat dmd = new SimpleDateFormat("EEEE, MMMM dd");
        now_dmd = dmd.format(today);
        tom_dmd = dmd.format(tomorrow);

        DateFormat d = new SimpleDateFormat("EEEE");
        tod_d = d.format(today);
        tom_d = d.format(tomorrow);

        dateTextView.setText(now_dmd);

        dateSwitch = (Switch) findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateTextView.setText(isChecked ? tom_dmd : now_dmd);
                updateTimes();
            }
        });

        updateTimes();

    }


    public void onButtonClick(View v) {
        Intent intent = new Intent(this, DiningMenuActivity.class);

        switch (v.getId()) {
            case R.id.BreakfastButton:
                intent.putExtra(EXTRA_MEAL, "Breakfast");
                break;
            case R.id.LunchButton:
                intent.putExtra(EXTRA_MEAL, "Lunch");
                break;
            case R.id.DinnerButton:
                intent.putExtra(EXTRA_MEAL, "Dinner");
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

        intent.putExtra(EXTRA_DATE, !dateSwitch.isChecked() ? now_dmd : tom_dmd);
        startActivity(intent);

    }

    private void updateTimes() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MealTimes");
        query.whereEqualTo("dayOfWeek", !dateSwitch.isChecked() ? tod_d : tom_d);
//        query.whereEqualTo("dayOfWeek", "Thursday");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject item : objects) {
                        switch (item.getString("meal")){
                            case "Breakfast":
                                breakfastButton.setText("Breakfast\n".concat(item.getString("time")));
                                break;
                            case "Lunch":
                                lunchButton.setText("Lunch\n".concat(item.getString("time")));
                                break;
                            case "Dinner":
                                dinnerButton.setText("Dinner\n".concat(item.getString("time")));
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