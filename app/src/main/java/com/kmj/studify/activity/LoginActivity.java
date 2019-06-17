package com.kmj.studify.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.kmj.studify.LoginCallback;
import com.kmj.studify.R;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private LoginButton btn_facebook_login;
    private ConstraintLayout custom_login_btn;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_facebook_login = findViewById(R.id.login_button);
        custom_login_btn = findViewById(R.id.custom_login_button);


        AccessToken accessToken = AccessToken.getCurrentAccessToken(); //앱에 저장 된토큰 가져오기
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn)
            Log.e("isLoggedIn", "True" + accessToken);
        else {
            Log.e("isLoggedIn", "false");
        }

        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();

        custom_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn_facebook_login.performClick();
                login();
            }
        });


        btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);

    }

    public void login() {
        LoginManager.getInstance().logInWithReadPermissions(
                this
                , Arrays.asList("public_profile","user_friends","email",""));  // 필요한 권한들 요청하기
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}

