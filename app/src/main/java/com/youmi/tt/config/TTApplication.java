package com.youmi.tt.config;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;
import com.zhy.autolayout.config.AutoLayoutConifg;


/**
 * Created by hx on 2016/11/23.
 */
public class TTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        initNetwork();
    }

    private void initNetwork() {
        NoHttp.initialize(this);
    }
}
