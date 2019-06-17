package com.kmj.studify;


import com.kmj.studify.data.RegisterModel;
import com.kmj.studify.data.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkInterface {
    @POST("/user/register")
    @FormUrlEncoded
    Call<RegisterModel> Register(@Field("name") String name, @Field("facebookId") String facebookId, @Field("profileURL") String profileURL);


}
