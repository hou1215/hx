package com.youmi.tt.base;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.youmi.tt.utils.CommonUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import static com.youmi.tt.utils.ActivityUtil.goToActivityFromRight2Left;


/**
 * Activity 基类
 *
 * @Author hx on 2016/10/31.
 */
public class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {



    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setTranslucentStatus();
    }

    public Activity getContext() {
        return context;
    }

    /**
     * 设置 透明状态栏
     */
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 启动
     * @param clz
     */
    public void startActivity(Class<?> clz ){
        goToActivityFromRight2Left(context,clz);
    }

    /**
     * 设置点击监听
     */
    public void setOnClickListener(View view) {
        CommonUtils.setOnClickListener(view, this);
    }

    /**
     * 设置view 可见性
     */
    public void setViewVisible(View view, boolean... isVisible) {
        CommonUtils.setViewVisible(view, isVisible);
    }

    /**
     * 设置view 选中
     */
    public void setViewSelect(View view, boolean... isSelect) {
        CommonUtils.setViewSelect(view, isSelect);
    }

    /**
     * 设置 文本
     */
    public void setText(TextView view, CharSequence text) {
        CommonUtils.setText(view, text);
    }

    /**
     * 显示Toast
     */
    public void showToast(Object msg) {
        toast(String.valueOf(msg));
    }
    /**
     * toast
     */
    public void toast(CharSequence text) {
        CommonUtils.toast(getContext(), text);
    }
    /**
     * 加载 drawable
     */
    public Drawable getDrawable_(int resId) {
        return CommonUtils.getDrawable_(getContext(), resId);
    }

    /**
     * 加载 color
     */
    public int getColor_(int resId) {
        return CommonUtils.getColor_(getContext(), resId);
    }

    /**
     * 设置 color
     * eg: 0xff00ff00 16进制
     */
    public static void setViewColor(View view, int color_hex) {
        CommonUtils.setViewColor(view, color_hex);
    }

    /**
     * 设置 color
     * eg: R.color|drawable.xx
     */
    public static void setViewColorRes(View view, int resId) {
        CommonUtils.setViewColorRes(view, resId);
    }


    @Override
    public void onClick(View view) {

    }
}
