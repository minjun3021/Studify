package com.kmj.studify.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kmj.studify.R;

public class StudyfinishActivity extends Activity {
    TextView time;
    Double amount;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_studyfinish);
        time=findViewById(R.id.txtText);
        btn=findViewById(R.id.endbtn);
        Intent intent=getIntent();
        amount=intent.getDoubleExtra("amount",0);
        int hour, min, sec;
        hour = (int) (amount / 3600);
        min = (int) (amount% 3600 / 60);
        sec = (int) (amount% 3600 % 60);
        time.setText(hour+" 시간 "+min+" 분 "+sec+" 초");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
