package com.kmj.studify.activity;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kmj.studify.LoginCallback;
import com.kmj.studify.R;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private LoginButton btn_facebook_login;
    private Button custom_login_btn;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn)
            Log.e("isLoggedIn", "True" + accessToken);
        else {
            Log.e("isLoggedIn", "false");
        }
        btn_facebook_login = findViewById(R.id.login_button);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();
        custom_login_btn=findViewById(R.id.custom_login_button);
        custom_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_facebook_login.performClick();
            }
        });


        btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}

