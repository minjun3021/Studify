package com.kmj.studify.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.kmj.studify.AlwaysOnTopService;
import com.kmj.studify.R;

public class LockSetActivity extends Activity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    private static final int REQ_CODE_OVERLAY_PERMISSION = 1;
    EditText hours,mins;
    TextView yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lock_set);
        yes=findViewById(R.id.settime_yes);
        no=findViewById(R.id.settime_no);
        hours=findViewById(R.id.settime_hour);
        mins=findViewById(R.id.settime_min);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openView();
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
    public void openView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(LockSetActivity.this, AlwaysOnTopService.class);
                intent.putExtra("Hour",hours.getText().toString());
                intent.putExtra("Minute",mins.getText().toString());
                startService(intent);
            }


            else
                onObtainingPermissionOverlayWindow();
        }
    }

    public void closeView() {
        stopService(new Intent(this, AlwaysOnTopService.class));
    }

    public void onObtainingPermissionOverlayWindow() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION);
    }
}
