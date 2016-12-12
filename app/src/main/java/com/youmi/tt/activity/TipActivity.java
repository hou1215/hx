package com.youmi.tt.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.adapter.OrderTipAdapter;
import com.youmi.tt.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TipActivity extends BaseActivity {

    @Bind(R.id.listview) ListView listView;
    @Bind(R.id.action) TextView action;
    private List<String> lists;
    private OrderTipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        ButterKnife.bind(this);   //绑定ButterKnife

        setData();
        adapter = new OrderTipAdapter(this,lists);
        listView.setAdapter(adapter);


    }

    @OnClick(R.id.action)
    public void onActionClick(){
        startActivity(CheckOrderActivity.class);
    }

    private void setData() {
        lists = new ArrayList<>();
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
        lists.add("糖醋鱼鸡蛋饭");
    }
}
