package com.kmj.studify;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AlwaysOnTopService extends Service {

    private WindowManager.LayoutParams params;

    WindowManager wm;
    View mView;


    ProgressBar progressBar;

    private static final int MILLISINFUTURE = 11 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private static final int HOUR = 3600 * 1000;
    private static final int MINUTE = 60 * 1000;

    TextView leftTimeHour;
    TextView leftTimeMin;
    TextView leftTimeSec;
    int timeH, timeM, timeS = 60;

    CountDownTimer countDownTimer;
    int TotalTime;







    @Override
    public IBinder onBind(Intent arg) {
        return null;
    }

    //
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return Service.START_STICKY; //서비스가 종료되어도 자동으로 다시 실행시켜줘!
        } else {
            Log.e("intent",intent.getStringExtra("Hour")+"");
            String strH = intent.getStringExtra("Hour");
            String strM = intent.getStringExtra("Minute");
            intent = new Intent(getApplicationContext(), LockActivity.class);

            timeH = Integer.parseInt(strH);
            timeM = Integer.parseInt(strM);
            CountDownTimer();
            countDownTimer.start();
            ProgressBarRunning();
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("AlwaysOnTopService ","11");
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = inflate.inflate(R.layout.activity_lock, null);

        int layout_parms;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layout_parms = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        } else {

            layout_parms = WindowManager.LayoutParams.TYPE_PHONE;

        }
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                layout_parms,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);





        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, params);


    }

    public void ProgressBarRunning() {
        progressBar = mView.findViewById(R.id.circular_progress_bar);
        ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        anim.setDuration(TotalTime);
        anim.start();
    }

    public void CountDownTimer() {
        leftTimeHour = mView.findViewById(R.id.left_time_hour);
        leftTimeMin = mView.findViewById(R.id.left_time_min);
        leftTimeSec = mView.findViewById(R.id.left_time_second);

        TotalTime = (timeH * HOUR) + (timeM * MINUTE);

        countDownTimer = new CountDownTimer(TotalTime, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

                int second = (int)(millisUntilFinished / 1000);

                leftTimeHour.setText(String.valueOf(second / 3600));

                leftTimeMin.setText(String.valueOf((second % 3600) / 60));

                leftTimeSec.setText(String.valueOf((second % 3600) % 60));

            }


            @Override

            public void onFinish() {
                leftTimeHour.setText("00");
                leftTimeMin.setText("00");
                leftTimeSec.setText("00");
                stopService(new Intent(AlwaysOnTopService.this, AlwaysOnTopService.class));

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wm != null) {
            if (mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            wm = null;

        }
    }


}

