package com.kmj.studify.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kmj.studify.R;
import com.kmj.studify.adapter.SelectAdapter;
import com.kmj.studify.data.Subject;

import java.util.ArrayList;

public class SelectActivity extends Activity implements SelectAdapter.OnItemClickListener {
    ListView listview;
    EditText editText;
    ImageView add;
    TextView ok;
    ArrayList<Subject> subject;
    boolean select=false;
    int selectItemPosition=0;
    SelectAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select);
        listview=findViewById(R.id.select_list);
        editText=findViewById(R.id.select_edit);
        add=findViewById(R.id.select_add);
        ok=findViewById(R.id.select_ok);
        subject=new ArrayList<>();
        mAdapter=new SelectAdapter(this,subject,R.layout.select_item);
        listview.setAdapter(mAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject newSub=new Subject(editText.getText().toString(),false);
                subject.add(newSub);
                editText.setText("");
                Log.e("click",subject.size()+"");
                mAdapter.notifyDataSetChanged();
            }
        });
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            parent.getChildAt(selectItemPosition).setBackgroundColor(Color.TRANSPARENT);
            select=true;
            selectItemPosition=position;
            view.setBackgroundColor(Color.LTGRAY);
        }
    });
    ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(select && subject.size()>0){

                Intent resultIntent = new Intent();
                resultIntent.putExtra("subName",subject.get(selectItemPosition).getName());
                setResult(RESULT_OK,resultIntent);
                finish();


            }
            else{
                Toast.makeText(SelectActivity.this, "과목을 선택하고 누르세요.", Toast.LENGTH_SHORT).show();
            }

        }
    });

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

    @Override
    public void itemDelete(int position) {
        subject.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void toNoBookMark(int position) {
        Subject temp=subject.get(position);
        subject.remove(position);
        temp.setBookmarked(false);

        subject.add(temp);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void toBookMark(int position) {
        Subject temp=subject.get(position);
        subject.remove(position);
        temp.setBookmarked(true);
        subject.add(0,temp);
        mAdapter.notifyDataSetChanged();
    }
}
