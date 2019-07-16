package com.kmj.studify.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmj.studify.R;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.activity.SelectActivity;
import com.kmj.studify.activity.StudyfinishActivity;
import com.kmj.studify.data.EndModel;
import com.kmj.studify.data.StartModel;
import com.kmj.studify.retrofit.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class TimerFragment extends Fragment implements SensorEventListener {
    MainActivity mainActivity;
    SensorManager sensorManager;


    Sensor proximitySensor;
    Handler mHandler;
    Sensor lightSensor;
    boolean isStarted = false;
    boolean isnear = false;
    ImageView select;
    int light = 1000;
    TextView subject;
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
        vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);

        SharedPreferences pref = mainActivity.getSharedPreferences("pref", MODE_PRIVATE);
        myToken = pref.getString("MyUserToken", "");
        Log.e("timerfragmenttoken",myToken);
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        subject = v.findViewById(R.id.subject);

        select = v.findViewById(R.id.timer_select);
        sensorManager = (SensorManager) mainActivity.getSystemService(mainActivity.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (proximitySensor == null) {
            Toast.makeText(mainActivity, "No Proximity Sensor Found", Toast.LENGTH_SHORT).show();
        }
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                seconds++;

                // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
                mHandler.sendEmptyMessageDelayed(0, 1000);


            }


        };
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject.setText("공부 선택");
                Intent intent = new Intent(mainActivity, SelectActivity.class);
                startActivityForResult(intent, 3000);
            }
        });


        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3000:
                    subject.setText(data.getStringExtra("subName"));
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] == 0) {
                isnear = true;
                Log.e("Near : ", String.valueOf(event.values[0]));
            } else {
                isnear = false;
                Log.e("Far : ", String.valueOf(event.values[0]));
            }
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightLux = event.values[0];
            lightLux = (float) (Math.round(lightLux * 100) / 100.0);
            int color = (int) lightLux;

            light = color;


        }

        if (isnear && light < 5 || light<1) {
            if (isStarted == false) {
                mHandler.sendEmptyMessage(0);
                isStarted = true;
                vibrator.vibrate(500);
                Log.e("token", myToken);
                String subject1 = subject.getText().toString();

                NetworkHelper.getInstance().Start(myToken, subject1).enqueue(new Callback<StartModel>() {
                    @Override
                    public void onResponse(Call<StartModel> call, Response<StartModel> response) {

                        Window mywindow = mainActivity.getWindow();
                        WindowManager.LayoutParams lp = mywindow.getAttributes();
                        lp.screenBrightness = 0;
                        mywindow.setAttributes(lp);


                    }

                    @Override
                    public void onFailure(Call<StartModel> call, Throwable t) {
                        Log.e("Ohmygoderror", t.toString());
                    }
                });
            }

        } else {
            if (isStarted) {
                isStarted = false;
                mHandler.removeCallbacksAndMessages(null);
                vibrator.vibrate(500);
                NetworkHelper.getInstance().End(myToken).enqueue(new Callback<EndModel>() {
                    @Override
                    public void onResponse(Call<EndModel> call, Response<EndModel> response) {
                        Window mywindow = mainActivity.getWindow();
                        WindowManager.LayoutParams lp = mywindow.getAttributes();
                        lp.screenBrightness = 1;
                        mywindow.setAttributes(lp);

                        Intent intent=new Intent(mainActivity, StudyfinishActivity.class);
                        intent.putExtra("amount",response.body().getAmount());
                        startActivity(intent);
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
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
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


    public void On() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void Off() {

        sensorManager.unregisterListener(this);
    }
}
