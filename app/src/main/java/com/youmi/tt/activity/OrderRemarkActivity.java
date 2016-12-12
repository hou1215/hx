package com.youmi.tt.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderRemarkActivity extends BaseActivity {

    @Bind(R.id.tab_title)
    TextView tab_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remark);
        ButterKnife.bind(this);   //绑定ButterKnife

        setText(tab_title,"订单备注");
    }
}
