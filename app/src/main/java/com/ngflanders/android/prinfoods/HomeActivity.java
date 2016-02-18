package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseObject;

public class HomeActivity extends AppCompatActivity {

    private Button diningButton;
    private Button pubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("nick", "isawesome");
        testObject.saveInBackground();

    }

    public void launchDiningActivity(View view) {
        Intent intent = new Intent(this, DiningHoursActivity.class);
        startActivity(intent);
    }

    public void launchPubActivity(View view) {
        Intent intent = new Intent(this, PubHoursActivity.class);
        startActivity(intent);
    }


}
