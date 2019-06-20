package com.kmj.studify.activity;



import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
        fragments = new ArrayList<>();
        timerFragment = new TimerFragment();
        rankingFragment = new RankingFragment();
        friendsFragment = new FriendsFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        fragments.add(timerFragment);

        fragments.add(friendsFragment);
        fragments.add(rankingFragment);
        fragmentUtils = new FragmentUtils(R.id.frame_layout, fragments);
        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
        final MenuItem timer=bottomNavigationView.getMenu().getItem(0);
        final MenuItem friends=bottomNavigationView.getMenu().getItem(1);
        final MenuItem ranking=bottomNavigationView.getMenu().getItem(2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_timer:
                        timer.setIcon(R.drawable.on_timer);
                        friends.setIcon(R.drawable.friends);
                        ranking.setIcon(R.drawable.ranking);
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
                        break;
                    case R.id.navigation_friends:
                        timer.setIcon(R.drawable.timer);
                        friends.setIcon(R.drawable.on_friends);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            friends.setIconTintList(ColorStateList.valueOf(0xffebb42c));
                        }
                        ranking.setIcon(R.drawable.ranking);
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 1, new Bundle());
                        break;
                    case R.id.navigation_ranking:
                        timer.setIcon(R.drawable.timer);
                        friends.setIcon(R.drawable.friends);
                        ranking.setIcon(R.drawable.on_ranking);
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 2, new Bundle());
                        break;
                }
                return false;
            }
        });


    }
}
