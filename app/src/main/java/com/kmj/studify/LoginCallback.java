package com.kmj.studify;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginCallback implements FacebookCallback<LoginResult> {
    // 로그인 성공 시 호출 됩니다. Access Token 발급 성공.
    String userID;

    @Override

    public void onSuccess(LoginResult loginResult) {

        Log.e("Callback :: ", "onSuccess");
        getMyInformation(loginResult.getAccessToken());
        requestMe(loginResult.getAccessToken());
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

    public static void requestMe(final AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                new GraphRequest(
                        token,
                        // "/me/friends",
                        //"me/taggable_friends",
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                try {
                                    Log.e("Json Array", response.toString());
                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    Log.e("Json Array Length ", "Json Array Length " + rawName.length());
                                    Log.e("Json Array", "Json Array " + rawName.toString());


                                    for (int i = 0; i < rawName.length(); i++) {
                                        JSONObject c = rawName.getJSONObject(i);


                                        String name = c.getString("name");
                                        Log.e("Friends's Name", "JSON NAME :" + name);

                                        String id = c.getString("id");
                                        Log.e("Friends's ID :", name+"'s ID:" + id);


                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                ).executeAsync();


            }


        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,profile_pic");
        request.setParameters(parameters);
        request.executeAsync();
    }
    public static void getMyInformation(AccessToken accessToken){
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

