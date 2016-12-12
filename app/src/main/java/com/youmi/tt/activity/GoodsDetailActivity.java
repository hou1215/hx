package com.youmi.tt.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.tab_title)
    TextView tab_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        setText(tab_title,"商品详情");
    }
}
