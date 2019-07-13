package com.kmj.studify.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.kmj.studify.FriendsAdapter;
import com.kmj.studify.NetworkHelper;
import com.kmj.studify.R;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FriendsFragment extends Fragment {
    ArrayList<UserModel> friendsranking;
    MainActivity mainActivity;
    String friendsFacebookIds = "";
    CircleImageView mypic;
    Handler mHandler;

    TextView tvname;
    Handler handler;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FriendsAdapter friendsAdapter;


    public FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        mainActivity = (MainActivity) getActivity();
        mRecyclerView = v.findViewById(R.id.friends_recycler);
        mLinearLayoutManager = new LinearLayoutManager(mainActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mypic = v.findViewById(R.id.myPic);
        SharedPreferences pref = mainActivity.getSharedPreferences("pref", MODE_PRIVATE);
        String name = pref.getString("name", "");
        String facebookId = pref.getString("facebookId", "");
        String profileURL = pref.getString("profileURL", "");
        tvname = v.findViewById(R.id.myName);
        tvname.setText(name);
        Glide.with(mainActivity)
                .load(profileURL)
                .fitCenter()
                .into(mypic);

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                new GraphRequest(
                        accessToken,
                        // "/me/friends",
                        //"me/taggable_friends",
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                try {

                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    Log.e("Json Array Length ", "Json Array Length " + rawName.length());
                                    Log.e("Json Array", "Json Array " + rawName.toString());


                                    for (int i = 0; i < rawName.length(); i++) {
                                        JSONObject c = rawName.getJSONObject(i);


                                        String name = c.getString("name");
                                        Log.e("Friends's Name", "JSON NAME :" + name);

                                        String id = c.getString("id");
                                        Log.e("Friends's ID :", name + "'s ID:" + id);
                                        if (i == rawName.length() - 1) {
                                            friendsFacebookIds += id;
                                        } else {
                                            friendsFacebookIds += id + ",";
                                        }

                                    }
                                    Log.e("friendsFacebookIds", friendsFacebookIds);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("trycatch실패", e.toString());
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
        friendsranking = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NetworkHelper.getInstance().FriendsRanking(friendsFacebookIds).enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        Log.e("rankingretrofit", response.toString());
                        friendsranking = response.body();
                          Log.e("How many", String.valueOf(friendsranking.size()));
                        if (friendsranking.size() != 0) {
                            Log.e("1st", friendsranking.get(0).getName());
                            Log.e("1st", friendsranking.get(0).getFacebookId());
                        }

                        friendsAdapter = new FriendsAdapter(friendsranking, mainActivity);
                        mRecyclerView.setAdapter(friendsAdapter);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                        Log.e("ohmygoderror", toString());
                    }
                });
            }
        }, 1500);
        TimerTask adTast = new TimerTask() {

            public void run() {
                Log.e("timer","good");
                NetworkHelper.getInstance().FriendsRanking(friendsFacebookIds).enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        Log.e("rankingretrofit", response.toString());
                        friendsranking = response.body();
                        Log.e("How many", String.valueOf(friendsranking.size()));
                        if (friendsranking.size() != 0) {
                            Log.e("1st", friendsranking.get(0).getName());
                            Log.e("1st", friendsranking.get(0).getFacebookId());
                        }

                        friendsAdapter = new FriendsAdapter(friendsranking, mainActivity);
                        mRecyclerView.setAdapter(friendsAdapter);
                        friendsAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                        Log.e("ohmygoderror", toString());
                    }
                });

            }

        };
        Timer timer = new Timer();


        timer.schedule(adTast, 0, 1000);






        return v;
    }



}