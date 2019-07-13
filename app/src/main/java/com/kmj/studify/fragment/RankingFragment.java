package com.kmj.studify.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kmj.studify.NetworkHelper;
import com.kmj.studify.R;
import com.kmj.studify.RankingAdapter;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.UserModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankingFragment extends Fragment {
    ArrayList<UserModel> ranking;
    MainActivity mainActivity;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RankingAdapter rankingAdapter;

    CircleImageView circleImageView;
    TextView avg;
    TextView max;
    TextView name;
    public RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, container, false);
        mainActivity= (MainActivity) getActivity();
        circleImageView=v.findViewById(R.id.rank_1st);
        name=v.findViewById(R.id.rank_1st_name);
        avg=v.findViewById(R.id.rank_1st_avg);
        max=v.findViewById(R.id.rank_1st_best);
        mRecyclerView=v.findViewById(R.id.ranking_recycler);
        mLinearLayoutManager=new LinearLayoutManager(mainActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ranking=new ArrayList<>();
        NetworkHelper.getInstance().Ranking().enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                Log.e("rankingretrofit",response.toString());
                ranking=response.body();
                Log.e("How many",String.valueOf(ranking.size()));
                Log.e("1st",ranking.get(0).getName());
                Log.e("1st",ranking.get(0).getFacebookId());
                Glide.with(mainActivity)
                        .load(ranking.get(0).getProfileURL())
                        .fitCenter()
                        .into(circleImageView);
                int hour, min, sec;
                hour = (int) (ranking.get(0).getMax_time() / 3600);
                min = (int) (ranking.get(0).getMax_time() % 3600 / 60);
                sec = (int) (ranking.get(0).getMax_time() % 3600 % 60);
                String maxs = String.valueOf(hour) + " : " + String.valueOf(min) + " : " + String.valueOf(sec);
                max.setText("최대 공부 시간\n"+maxs);

                hour = (int) (ranking.get(0).getAverage_time() / 3600);
                min = (int) (ranking.get(0).getAverage_time() % 3600 / 60);
                sec = (int) (ranking.get(0).getAverage_time() % 3600 % 60);
                String avgs = String.valueOf(hour) + " : " + String.valueOf(min) + " : " + String.valueOf(sec);
                avg.setText("평균 공부 시간\n"+avgs);
                name.setText(ranking.get(0).getName());
                ranking.remove(0);

                rankingAdapter=new RankingAdapter(ranking,mainActivity);
                mRecyclerView.setAdapter(rankingAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                Log.e("ohmygoderrror",t.toString());
            }
        });



        return v;
    }

}