package com.ngflanders.android.prinfoods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DiningHoursActivity extends AppCompatActivity {

    private TextView dateTextView;
    private Switch dateSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hours);

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        Calendar c = Calendar.getInstance();
        final Date today = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, 1);
        final Date tomorrow = c.getTime();

        DateFormat df = new SimpleDateFormat("EEEE, MMM dd");
        final String now = df.format(today);
        final String tom = df.format(tomorrow);

        dateTextView.setText(now);

        dateSwitch = (Switch) findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateTextView.setText(isChecked ? tom : now);
            }
        });


    }
}
