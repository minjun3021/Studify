package com.kmj.studify.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.kmj.studify.LoginCallback;
import com.kmj.studify.NetworkHelper;
import com.kmj.studify.R;

import com.kmj.studify.data.RegisterModel;
import com.kmj.studify.data.UserModel;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private LoginButton btn_facebook_login;
    private ConstraintLayout custom_login_btn;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;
    Button guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

guest=findViewById(R.id.login_guestbtn);
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
        mLoginCallback = new LoginCallback(getApplicationContext());
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

            }
        });
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
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String name=pref.getString("name", "");
        String facebookId=pref.getString("facebookId", "");
        String profileURL=pref.getString("profileURL", "");
        NetworkHelper.getInstance().Register(name,facebookId,profileURL).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                SharedPreferences pref =getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MyUserToken",response.body().getUserModel().getToken());
                editor.commit();
                Log.e("mymyToken",response.body().getUserModel().getToken());
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {

            }
        });




        super.onActivityResult(requestCode, resultCode, data);
    }

}

