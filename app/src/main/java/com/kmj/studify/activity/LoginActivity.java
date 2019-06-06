package com.kmj.studify.activity;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kmj.studify.R;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private LoginButton btn_facebook_login;


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


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override //성공후 엑세스토큰 발급받음
            public void onSuccess(LoginResult loginResult) {
                requestMe(loginResult.getAccessToken());


            }

            @Override //로그인 창을 닫을경우
            public void onCancel() {
                Log.e("Callback :: ", "onCancel");
            }

            @Override //로그인 실패 할경우
            public void onError(FacebookException error) {
                Log.e("Callback :: ", "onError : " + error.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    // 사용자 정보 요청

    // 사용자 정보 요청

    public void requestMe(AccessToken token) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(token,

                new GraphRequest.GraphJSONObjectCallback() {

                    @Override

                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.e("result", object.toString());

                    }

                });


        Bundle parameters = new Bundle();

        parameters.putString("fields", "id,name,email,gender,birthday");

        graphRequest.setParameters(parameters);

        graphRequest.executeAsync();

    }
}

