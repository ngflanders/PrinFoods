package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private ImageButton diningButton;
    private ImageButton pubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        pubButton = (ImageButton) findViewById(R.id.pubButton);
        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "pub is clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void launchDiningActivity(View view) {
        Intent intent = new Intent(this, DiningHoursActivity.class);
        startActivity(intent);
    }


}
