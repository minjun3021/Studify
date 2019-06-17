package com.kmj.studify.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kmj.studify.R;
import com.kmj.studify.activity.MainActivity;

public class TimerFragment extends Fragment {
    MainActivity mainActivity;
    TextView hour, minute, second;
    Button start;
    Handler mHandler;
    int hours = 0, minutes = 0, seconds = 0;

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                seconds++;
                second.setText(String.valueOf(seconds));

                // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
                mHandler.sendEmptyMessageDelayed(0, 1000);


            }



        };
        mainActivity = (MainActivity) getActivity();
        hour = v.findViewById(R.id.timer_hour);
        minute = v.findViewById(R.id.timer_minute);
        second = v.findViewById(R.id.timer_second);
        start = v.findViewById(R.id.timer_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(0);
            }
        });


        return v;

    }
}
