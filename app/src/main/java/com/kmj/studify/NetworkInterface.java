package com.kmj.studify;

import com.kmj.studify.data.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkInterface {
    @POST("/auth/login")
    @FormUrlEncoded
    Call<UserModel> Register(@Field("name") String name, @Field("facebookId") String facebookId,@Field("profileURL") String profileURL);


}
