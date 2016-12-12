package com.youmi.tt.activity;

import android.os.Bundle;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;

import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);   //绑定ButterKnife


    }
}
