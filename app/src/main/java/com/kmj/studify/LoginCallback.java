package com.kmj.studify;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginResult;
import com.kmj.studify.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class LoginCallback implements FacebookCallback<LoginResult> {
    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    String userID;
    Context context;

    public LoginCallback(Context context) {
        this.context = context;
    }

    @Override

    public void onSuccess(LoginResult loginResult) {

        Log.e("Callback :: ", "onSuccess");
        getMyInformation(loginResult.getAccessToken(),context);
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            Log.e("printToken", loginResult.getAccessToken().getToken());
            Log.e("printUserId", loginResult.getAccessToken().getUserId());
            userID = loginResult.getAccessToken().getUserId();
        }

    }


    // 로그인 창을 닫을 경우, 호출됩니다.

    @Override

    public void onCancel() {

        Log.e("Callback :: ", "onCancel");

    }


    // 로그인 실패 시에 호출됩니다.

    @Override

    public void onError(FacebookException error) {

        Log.e("Callback :: ", "onError : " + error.getMessage());
    }


    // 사용자 정보 요청


    public static void getMyInformation(AccessToken accessToken, final Context context){
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.e("MyinFor",response.toString());
                        try {
                            Log.e("Myname",object.getString("name"));
                            Log.e("Myid",object.getString("id"));
                            String name=object.getString("name");
                            String facebookId=object.getString("id");
                            Log.e("Myname",name);
                            Log.e("Myid",facebookId);

                            SharedPreferences pref =context.getSharedPreferences("pref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putString("name",name);
                            editor.putString("facebookId",facebookId);
                            editor.putString("profileURL","http://graph.facebook.com/"+facebookId+"/picture?type=large");
                            editor.commit();

                            LoginActivity.register(context,name,facebookId,"http://graph.facebook.com/"+facebookId+"/picture?type=large");


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }
}