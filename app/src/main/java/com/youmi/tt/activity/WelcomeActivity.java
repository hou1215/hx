package com.youmi.tt.activity;

import android.os.Handler;
import android.os.Bundle;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        goHome();
    }

    public void goHome() {

        final Class<?> activityClass;
        final long second; // 延时
        Integer area_id = 50;

        if (area_id == null || area_id <= 0) {
            second = 2000;
            activityClass = MainActivity.class;

        } else {
            activityClass = MainActivity.class;
            second = 2000;
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {

                // 启动引导页 | 主页
                startActivity(activityClass);
                finish();

            }
        }, second);
    }

}
