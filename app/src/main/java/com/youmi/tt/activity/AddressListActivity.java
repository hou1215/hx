package com.youmi.tt.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.youmi.tt.R;
import com.youmi.tt.adapter.AddressListAdapter;
import com.youmi.tt.base.BaseActivity;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.entity.Address;
import com.youmi.tt.view.recyclerview.RecyclerViewWrap;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressListActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    RecyclerViewWrap recyclerview;
    private LinearLayoutManager manager;
    private List<Address> lists;
    private AddressListAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);   //绑定ButterKnife

        lists = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Address address = new Address();
            lists.add(address);
        }

        addressAdapter = new AddressListAdapter(this,lists);
        manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        recyclerview.setHasFixedSize(true);

        // 使用 setIAdapter 不是setAdapter
        recyclerview.setAdapter(addressAdapter);

        addressAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, int... what) {
                finish();
            }
        });

    }
}
