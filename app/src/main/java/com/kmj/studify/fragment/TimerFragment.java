package com.kmj.studify.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kmj.studify.NetworkHelper;
import com.kmj.studify.R;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.EndModel;
import com.kmj.studify.data.StartModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TimerFragment extends Fragment implements SensorEventListener {
    MainActivity mainActivity;
    TextView hour, minute, second;
    Button start;
    private SensorManager sensorManager;
    Handler mHandler;
    boolean isStarted=false;
    boolean isnear=false;
    int light=1000;
    private Vibrator vibrator;
    String myToken;
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
        mainActivity = (MainActivity) getActivity();
        vibrator = (Vibrator)mainActivity.getSystemService(Context.VIBRATOR_SERVICE);

        SharedPreferences pref = mainActivity.getSharedPreferences("pref", MODE_PRIVATE);
        myToken=pref.getString("MyUserToken", "");
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        sensorManager = (SensorManager)mainActivity.getSystemService(mainActivity.SENSOR_SERVICE);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(proximitySensor == null) {
            Toast.makeText(mainActivity, "No Proximity Sensor Found", Toast.LENGTH_SHORT).show();
        }
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                seconds++;
                second.setText(String.valueOf(seconds));

                // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
                mHandler.sendEmptyMessageDelayed(0, 1000);


            }



        };
        hour = v.findViewById(R.id.timer_hour);
        minute = v.findViewById(R.id.timer_minute);
        second = v.findViewById(R.id.timer_second);
        start = v.findViewById(R.id.timer_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStarted==false){
                    mHandler.sendEmptyMessage(0);
                    isStarted=true;
                }

            }
        });


        return v;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(event.values[0] == 0) {
                isnear=true;
                Log.e("Near : ",String.valueOf(event.values[0]));
            } else {
                isnear=false;
                Log.e("Far : ",String.valueOf(event.values[0]));
            }
        }
        else if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            float lightLux = event.values[0];
            lightLux = (float) (Math.round(lightLux * 100)/100.0);
            int color = (int) lightLux;
            light=color;
            Log.e("LIGHT",String.valueOf(color));


        }

        if(isnear && light<2){
            if(isStarted==false){
                mHandler.sendEmptyMessage(0);
                isStarted=true;
                vibrator.vibrate(500);
                NetworkHelper.getInstance().Start(myToken,"Math").enqueue(new Callback<StartModel>() {
                    @Override
                    public void onResponse(Call<StartModel> call, Response<StartModel> response) {
                        Log.e("amount",String.valueOf(response.body().getAmount()));
                        Log.e("current",response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<StartModel> call, Throwable t) {
                        Log.e("Ohmygoderror","error");
                    }
                });
            }

        }
        else{
            if(isStarted){
                isStarted=false;
                mHandler.removeCallbacksAndMessages(null);
                vibrator.vibrate(500);
                NetworkHelper.getInstance().End(myToken).enqueue(new Callback<EndModel>() {
                    @Override
                    public void onResponse(Call<EndModel> call, Response<EndModel> response) {
                        Log.e("amount",String.valueOf(response.body().getAmount()));
                    }

                    @Override
                    public void onFailure(Call<EndModel> call, Throwable t) {

                    }
                });
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                Toast.makeText(mainActivity, "UNRELIABLE", Toast.LENGTH_SHORT).show();
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                Toast.makeText(mainActivity, "LOW", Toast.LENGTH_SHORT).show();
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                Toast.makeText(mainActivity, "MEDIUM", Toast.LENGTH_SHORT).show();
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                Toast.makeText(mainActivity, "HIGH", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
