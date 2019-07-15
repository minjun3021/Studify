package com.kmj.studify.activity;



import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.kmj.studify.FragmentUtils;
import com.kmj.studify.R;
import com.kmj.studify.fragment.FriendsFragment;
import com.kmj.studify.fragment.RankingFragment;
import com.kmj.studify.fragment.TimerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private TimerFragment timerFragment;
    private RankingFragment rankingFragment;
    static Toolbar mActionBarToolbar;
    private FriendsFragment friendsFragment;
    public static String friendsFacebookIds;
    ConstraintLayout timer,friends,ranking;
    ArrayList<Fragment> fragments;
    FragmentUtils fragmentUtils;
    ImageView ti,fi,ri;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.lock){

            Intent intent=new Intent(MainActivity.this,LockSetActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBarToolbar=findViewById(R.id.app_toolbar);
        mActionBarToolbar.setTitle("공부시간 측정");
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setTitle("공부시간 측정");
        timer=findViewById(R.id.timer);
        friends=findViewById(R.id.friends);
        ranking=findViewById(R.id.ranking);
        ti=findViewById(R.id.timer_img);
        fi=findViewById(R.id.friends_img);
        ri=findViewById(R.id.ranking_img);

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);
        }

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
        mActionBarToolbar.setTitle("공부시간 측정");
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.on_timer);
                fi.setImageResource(R.drawable.friends);
                ri.setImageResource(R.drawable.ranking);
                timerFragment.On();
                mActionBarToolbar.setTitle("공부시간 측정");
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.timer);
                fi.setImageResource(R.drawable.on_friends);
                ri.setImageResource(R.drawable.ranking);
                timerFragment.Off();
                mActionBarToolbar.setTitle("친구들의 공부상황");
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 1, new Bundle());
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti.setImageResource(R.drawable.timer);
                fi.setImageResource(R.drawable.friends);
                ri.setImageResource(R.drawable.on_ranking);
                mActionBarToolbar.setTitle("전체 랭킹");
                timerFragment.Off();
                fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 2, new Bundle());
            }
        });



    }



}
