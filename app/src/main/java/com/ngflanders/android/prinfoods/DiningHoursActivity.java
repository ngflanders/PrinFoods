package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DiningHoursActivity extends AppCompatActivity {

    static final String EXTRA_DATE = "com.example.EXTRA_DATE";
    static final String EXTRA_MEAL = "com.example.EXTRA_MEAL";

    private TextView dateTextView;
    private Switch dateSwitch;

    String now;
    String tom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hours);

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        Calendar c = Calendar.getInstance();
        final Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, 1);
        final Date tomorrow = c.getTime();

        DateFormat df = new SimpleDateFormat("EEEE, MMMM dd");
        now = df.format(today);
        tom = df.format(tomorrow);

        dateTextView.setText(now);

        dateSwitch = (Switch) findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateTextView.setText(isChecked ? tom : now);
            }
        });


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

        intent.putExtra(EXTRA_DATE, !dateSwitch.isChecked() ? now : tom);

        startActivity(intent);

    }


    public void launchDiningMenuActivity(View view) {
        Intent intent = new Intent(this, DiningMenuActivity.class);
        startActivity(intent);
    }

    private void updateData() {

    }
}