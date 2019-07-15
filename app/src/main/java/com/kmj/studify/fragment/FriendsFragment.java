package com.kmj.studify.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
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
import com.kmj.studify.adapter.FriendsAdapter;
import com.kmj.studify.retrofit.NetworkHelper;
import com.kmj.studify.activity.PopActivity;
import com.kmj.studify.R;
import com.kmj.studify.RecyclerTouchListener;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.Graph;
import com.kmj.studify.data.RecordModel;
import com.kmj.studify.data.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    String myToken;
    ConstraintLayout me;
    Handler mHandler;
    String name;
    ArrayList<RecordModel> record;
    TextView tvname;
    Handler handler;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FriendsAdapter friendsAdapter;
    ArrayList<Graph> toGraph;

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
        me = v.findViewById(R.id.myPro);
        mLinearLayoutManager = new LinearLayoutManager(mainActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mypic = v.findViewById(R.id.myPic);
        SharedPreferences pref = mainActivity.getSharedPreferences("pref", MODE_PRIVATE);
        name = pref.getString("name", "");
        String facebookId = pref.getString("facebookId", "");
        String profileURL = pref.getString("profileURL", "");
        myToken=pref.getString("MyUserToken","");
        Log.e("friendsfragment",myToken);
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

        Log.e("friends", friendsFacebookIds);
        NetworkHelper.getInstance().FriendsRanking(friendsFacebookIds).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                Log.e("rankingretrofit", response.toString());
                friendsranking = response.body();
                Log.e("How many", String.valueOf(friendsranking.size()));

                friendsAdapter = new FriendsAdapter(friendsranking, mainActivity);
                mRecyclerView.setAdapter(friendsAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Log.e("ohmygoderror", toString());
            }
        });


        TimerTask adTast = new TimerTask() {

            public void run() {
                Log.e("timer", "good");
                NetworkHelper.getInstance().FriendsRanking(friendsFacebookIds).enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        Log.e("rankingretrofit", response.toString());
                        friendsranking = response.body();
                        Log.e("How many", String.valueOf(friendsranking.size()));


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
        long nowmill = System.currentTimeMillis();

        final Date now = new Date(nowmill);
        final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        record = new ArrayList<>();

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkHelper.getInstance().Record(myToken).enqueue(new Callback<ArrayList<RecordModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RecordModel>> call, Response<ArrayList<RecordModel>> response) {
                        record = response.body();
                        toGraph = new ArrayList<>();

                        for (int i = 0; i < record.size(); i++) {
                            Date secondDate = null;
                            try {

                                secondDate = format.parse(record.get(i).getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long calDate = now.getTime() - secondDate.getTime();
                            long calDateDays = calDate / (24 * 60 * 60 * 1000);
                            String time1 = format.format(now);
                            String time2 = format.format(secondDate);
                            Log.e("time1", time1);
                            Log.e("time2", time2);
                            Log.e("날짜차이", "Record[" + i + "] 의 날짜차이 : " + String.valueOf(calDateDays));
                            if (calDateDays >5) {
                                break;
                            } else { //5일 이내일때
                                toGraph.add(0, new Graph(record.get(i).getAmount(), record.get(i).getDate(),name));
                            }
                        }


                        Intent intent = new Intent(mainActivity, PopActivity.class);
                        intent.putExtra("data", toGraph);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RecordModel>> call, Throwable t) {

                    }
                });
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(mainActivity, mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                NetworkHelper.getInstance().Record(friendsranking.get(position).getToken()).enqueue(new Callback<ArrayList<RecordModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RecordModel>> call, Response<ArrayList<RecordModel>> response) {
                        record = response.body();
                        toGraph = new ArrayList<>();
                        Log.e("amount", String.valueOf(record.get(0).getAmount()));
                        for (int i = 0; i < record.size(); i++) {
                            Date secondDate = null;
                            try {

                                secondDate = format.parse(record.get(i).getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long calDate = now.getTime() - secondDate.getTime();
                            long calDateDays = calDate / (24 * 60 * 60 * 1000);
                            String time1 = format.format(now);
                            String time2 = format.format(secondDate);
                            Log.e("time1", time1);
                            Log.e("time2", time2);
                            Log.e("날짜차이", "Record[" + i + "] 의 날짜차이 : " + String.valueOf(calDateDays));
                            if (calDateDays > 5) {
                                break;
                            } else { //5일 이내일때
                                toGraph.add(0, new Graph(record.get(i).getAmount(), record.get(i).getDate(), friendsranking.get(position).getName()));
                            }
                        }
                        Intent intent = new Intent(mainActivity, PopActivity.class);
                        intent.putExtra("data", toGraph);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RecordModel>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return v;
    }


}