package com.kmj.studify.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kmj.studify.NetworkHelper;
import com.kmj.studify.R;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankingFragment extends Fragment {
    ArrayList<UserModel> ranking;
    MainActivity mainActivity;
    Button btn;
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

//        btn=v.findViewById(R.id.ranking_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NetworkHelper.getInstance().Ranking().enqueue(new Callback<ArrayList<UserModel>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
//                        Log.e("rankingretrofit",response.toString());
//                        ranking=response.body();
//                        Log.e("How many",String.valueOf(ranking.size()));
//                        Log.e("1st",ranking.get(0).getName());
//                        Log.e("1st",ranking.get(0).getFacebookId());
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
//                        Log.e("ohmygoderrror",t.toString());
//                    }
//                });
//
//            }
//        });


        return v;
    }

}