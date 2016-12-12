package com.youmi.tt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOrderActivity extends BaseActivity {

    @Bind(R.id.rl_address)
    RelativeLayout rl_address;
    @Bind(R.id.tab_title)
    TextView tab_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        ButterKnife.bind(this);   //绑定ButterKnife

        setText(tab_title,"确认订单");

    }

    @OnClick({R.id.rl_address, R.id.tv_select_time,R.id.action,R.id.rl_remar,R.id.rl_send_time})
    public void onRlAddressClick(View v){

        switch (v.getId()){
            case R.id.rl_address:
                startActivity(AddressListActivity.class);
                break;
            case R.id.tv_select_time:

                break;
            case R.id.rl_send_time:
                //弹出选择配送时间
                break;
            case R.id.action:
                //弹出支付方式

                break;
            case R.id.rl_remar:
                //备注
                startActivity(OrderRemarkActivity.class);
                break;
        }

    }

}
