package com.kmj.studify.activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kmj.studify.FragmentUtils;
import com.kmj.studify.data.RegisterModel;
import com.kmj.studify.fragment.FriendsFragment;
import com.kmj.studify.R;
import com.kmj.studify.fragment.RankingFragment;
import com.kmj.studify.fragment.TimerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private TimerFragment timerFragment;
    private RankingFragment rankingFragment;
    private FriendsFragment friendsFragment;
    private BottomNavigationView bottomNavigationView;
    ArrayList<Fragment> fragments;
    FragmentUtils fragmentUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        Intent intent = getIntent();
//        RegisterModel registerModel=(RegisterModel)intent.getSerializableExtra("registerModel");
        //Log.e("mymytoken",registerModel.getUserModel().getToken());
        fragments=new ArrayList<>();
        timerFragment=new TimerFragment();
        rankingFragment=new RankingFragment();
        friendsFragment=new FriendsFragment();
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        fragments.add(timerFragment);
        fragments.add(rankingFragment);
        fragments.add(friendsFragment);
        fragmentUtils=new FragmentUtils(R.id.frame_layout,fragments);
        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(),0,new Bundle());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_timer:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(),0,new Bundle());
                        break;
                    case R.id.navigation_friends:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(),1,new Bundle());
                        break;
                    case R.id.navigation_ranking:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(),2,new Bundle());
                        break;
                }
                return false;
            }
        });



    }
}
