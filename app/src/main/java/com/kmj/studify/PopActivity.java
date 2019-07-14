package com.kmj.studify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.kmj.studify.data.Graph;

import java.util.ArrayList;

public class PopActivity extends Activity {
    Button btn;
    BarChart chart;
    ArrayList<Graph> record;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop);

        Intent intent=getIntent();
        record=(ArrayList<Graph>)intent.getSerializableExtra("data");
        btn=findViewById(R.id.pop_btn);
        textView=findViewById(R.id.pop_text);
        textView.setText(record.get(0).getName());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chart=findViewById(R.id.bar);
        Description desc ;
        Legend L;

        L = chart.getLegend();
        desc = chart.getDescription();
        desc.setTextAlign(Paint.Align.CENTER);
        desc.setText("시간"); // this is the weirdest way to clear something!!
        L.setEnabled(false);


        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();

        XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setLabelCount(record.size(),false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        String[] values = new String[5];
        for(int i=0; i<record.size(); i++){
            String date=record.get(i).getDate();
            String month=date.substring(4,6);
            String day=date.substring(6,8);
            values[i]=month+"월 "+day+"일";
        }

        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(values));

        leftAxis.setTextSize(10f);
        leftAxis.setLabelCount(7);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);

        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);

        BarData data = new BarData(setData());


        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);

        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        chart.animateXY(2000, 2000);
        chart.setDrawBorders(false);
        chart.setDescription(desc);
        chart.setDrawValueAboveBar(true);


    }

    private BarDataSet setData() {

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i<record.size(); i++){
            entries.add(new BarEntry(i, (float) (record.get(i).getAmount()/3600)));
        }






        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(Color.rgb(155, 155, 155));
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(155,155,155));

        return set;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }


}
