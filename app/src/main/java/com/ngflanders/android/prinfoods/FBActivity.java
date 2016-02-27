package com.ngflanders.android.prinfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FBActivity extends AppCompatActivity {

    private TextView fbinfo;
    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_fb);

        if (isLoggedIn()) {
            launchHomeActivity();
        }

        fbinfo = (TextView) findViewById(R.id.fb_info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // TODO clean up this string, add to strings.xml
                fbinfo.setText("User ID: " + loginResult.getAccessToken().getUserId() +
                        "\nAuth Token: " + loginResult.getAccessToken().getToken());
                launchHomeActivity();

// TODO is this a better way to log in and get user info?
//                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                try {
//                                    fbinfo.setText("Hi, " + object.getString("name"));
//                                } catch (JSONException ex) {
//                                    ex.printStackTrace();
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, name, email, gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//                launchHomeActivity();
            }


            @Override
            public void onCancel() {
                fbinfo.setText(R.string.login_cancel);
            }

            @Override
            public void onError(FacebookException error) {
                fbinfo.setText(R.string.login_error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void launchHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}
