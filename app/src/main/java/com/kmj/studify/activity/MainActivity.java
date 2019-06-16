package com.kmj.studify.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kmj.studify.fragment.FriendsFragment;
import com.kmj.studify.R;
import com.kmj.studify.fragment.RankingFragment;
import com.kmj.studify.fragment.TimerFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private TimerFragment timerFragment;
    private RankingFragment rankingFragment;
    private FriendsFragment friendsFragment;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        timerFragment=new TimerFragment();
        rankingFragment=new RankingFragment();
        friendsFragment=new FriendsFragment();
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){
                    case R.id.navigation_timer:
                        break;
                    case R.id.navigation_friends:
                        break;
                    case R.id.navigation_ranking:
                        break;
                }
            }
        });
    }
}
