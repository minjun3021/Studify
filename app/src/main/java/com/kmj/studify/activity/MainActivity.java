package com.kmj.studify.activity;



import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kmj.studify.FragmentUtils;
import com.kmj.studify.R;
import com.kmj.studify.fragment.FriendsFragment;
import com.kmj.studify.fragment.RankingFragment;
import com.kmj.studify.fragment.TimerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private TimerFragment timerFragment;
    private RankingFragment rankingFragment;
    private FriendsFragment friendsFragment;
    ConstraintLayout timer,friends,ranking;
    ArrayList<Fragment> fragments;
    FragmentUtils fragmentUtils;
    ImageView ti,fi,ri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=findViewById(R.id.timer);
        friends=findViewById(R.id.friends);
        ranking=findViewById(R.id.ranking);
        ti=findViewById(R.id.timer_img);
        fi=findViewById(R.id.friends_img);
        ri=findViewById(R.id.ranking_img);


//
//        Intent intent = getIntent();
//        RegisterModel registerModel=(RegisterModel)intent.getSerializableExtra("registerModel");
        //Log.e("mymytoken",registerModel.getUserModel().getToken());
        fragments = new ArrayList<>();
        timerFragment = new TimerFragment();
        rankingFragment = new RankingFragment();
        friendsFragment = new FriendsFragment();

        fragments.add(timerFragment);

        fragments.add(friendsFragment);
        fragments.add(rankingFragment);
        fragmentUtils = new FragmentUtils(R.id.frame_layout, fragments);
        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
        ti.setImageResource(R.drawable.on_timer);

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.on_timer);
                fi.setImageResource(R.drawable.friends);
                ri.setImageResource(R.drawable.ranking);
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.timer);
                fi.setImageResource(R.drawable.on_friends);
                ri.setImageResource(R.drawable.ranking);
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 1, new Bundle());
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.timer);
                fi.setImageResource(R.drawable.friends);
                ri.setImageResource(R.drawable.on_ranking);
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 2, new Bundle());
            }
        });



    }
}
