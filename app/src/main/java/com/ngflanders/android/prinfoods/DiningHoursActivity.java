package com.ngflanders.android.prinfoods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class DiningHoursActivity extends AppCompatActivity {

    private TextView dateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hours);

        dateTextView = (TextView) findViewById(R.id.dateTextView);

        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);

        dateTextView.setText(date);


    }
}
