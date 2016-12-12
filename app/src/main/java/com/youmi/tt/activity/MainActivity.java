package com.youmi.tt.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;
import com.youmi.tt.fragment.CategoryFragment;
import com.youmi.tt.fragment.HomeFragment;
import com.youmi.tt.fragment.MySelfFragment;
import com.youmi.tt.fragment.ShorCarFragment;
import com.youmi.tt.utils.FragmentManagerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private long outTime = 0;// 退出间隔时间

    private Fragment[] fragments;
    private int current_index = -1;


    @Bind(R.id.img_home) ImageView img_home;
    @Bind(R.id.img_myself) ImageView img_myself;
    @Bind(R.id.img_shopcart) ImageView img_shopcart;
    @Bind(R.id.img_store) ImageView img_store;

    @Bind(R.id.tv_home) TextView tv_home;
    @Bind(R.id.tv_myself) TextView tv_myself;
    @Bind(R.id.tv_shopcart) TextView tv_shopcart;
    @Bind(R.id.tv_store) TextView tv_store;
    @Bind(R.id.tv_shopcart_num) TextView tv_shopcart_num;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//设置底部菜单不跟输入框上移
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);   //绑定ButterKnife

        fragments = new Fragment[4];
        setTab(0);

        setText(tv_shopcart_num, 6 + "");
        setViewVisible(tv_shopcart_num, true);
    }



    // 切换 fragment
    private void setTab(int index) {

        if (index < 0 || index > fragments.length - 1) {
            index = 0;
        }

        Fragment f = fragments[index];

        setViewSelect(img_home);
        setViewSelect(img_store);
        setViewSelect(img_shopcart);
        setViewSelect(img_myself);

        setViewSelect(tv_home);
        setViewSelect(tv_store);
        setViewSelect(tv_shopcart);
        setViewSelect(tv_myself);

        switch (index) {

            case 1:
                if (f == null) {
                    f = new CategoryFragment();
                }
                setViewSelect(img_store, true);
                setViewSelect(tv_store, true);
                break;
            case 2:
                if (f == null) {
                    f = new ShorCarFragment();
                }
                setViewSelect(img_shopcart, true);
                setViewSelect(tv_shopcart, true);
                break;
            case 3:
                if (f == null) {
                    f = new MySelfFragment();
                }
                setViewSelect(img_myself, true);
                setViewSelect(tv_myself, true);
                break;
            default:
                if (f == null) {
                    f = new HomeFragment();
                }
                setViewSelect(img_home, true);
                setViewSelect(tv_home, true);
                break;
        }

        if (fragments[index] == null && f != null) {
            fragments[index] = f;
        }

        if (f != null && current_index != index) {
            current_index = index;
            FragmentManagerUtil.setForegroundFragment(getSupportFragmentManager(), f, R.id.rl_mainLayout);
        }

    }


    @OnClick({R.id.ll_home, R.id.ll_store, R.id.rl_shopcart, R.id.ll_myself})
    public void onTabClick(View v) {

        switch (v.getId()) {
            case R.id.ll_home:
                setTab(0);
                break;
            case R.id.ll_store:
                setTab(1);
                break;
            case R.id.rl_shopcart:
                setTab(2);
                break;
            case R.id.ll_myself:
                setTab(3);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        outTime = 0;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long now = System.currentTimeMillis() / 1000;
            if (now - outTime <= 2) {
                finish();
            } else {
                toast("再次点击，退出商城");
                outTime = now;
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
