package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.parse.Parse;

public class HomeActivity extends AppCompatActivity {

    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Parse.enableLocalDatastore(this);
        Parse.initialize(this);


    }


    public void launchDiningActivity(View view) {
        Intent intent = new Intent(this, DiningHoursActivity.class);
        startActivity(intent);
    }

    public void launchPubActivity(View view) {
        Intent intent = new Intent(this, PubHoursActivity.class);
        startActivity(intent);
    }

    public void launchCheckInActivity(View view) {
        Intent intent = new Intent(this, CheckInActivity.class);
        startActivity(intent);
    }

    public void launchFBActivity() {
        Intent intent = new Intent(this, FBActivity.class);
        startActivity(intent);
    }
}
