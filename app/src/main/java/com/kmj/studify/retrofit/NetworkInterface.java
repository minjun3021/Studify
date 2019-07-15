package com.kmj.studify.retrofit;


import com.kmj.studify.data.EndModel;
import com.kmj.studify.data.RecordModel;
import com.kmj.studify.data.RegisterModel;
import com.kmj.studify.data.StartModel;
import com.kmj.studify.data.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkInterface {
    @POST("/user/register")
    @FormUrlEncoded
    Call<RegisterModel> Register(@Field("name") String name, @Field("facebookId") String facebookId, @Field("profileURL") String profileURL);

    @POST("/user/start")
    @FormUrlEncoded
    Call<StartModel> Start(@Field("token")String token,@Field("current")String current);

    @POST("/user/end")
    @FormUrlEncoded
    Call<EndModel> End(@Field("token")String token);

    @POST("/user/ranking")
    Call<ArrayList<UserModel>> Ranking();

    @POST("/user/friend/ranking")
    @FormUrlEncoded
    Call<ArrayList<UserModel>> FriendsRanking(@Field("ids")String ids);

    @POST("/user/record")
    @FormUrlEncoded
    Call<ArrayList<RecordModel>> Record(@Field("token")String token);




}
