package com.youmi.tt.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youmi.tt.R;
import com.youmi.tt.activity.AddressListActivity;
import com.youmi.tt.activity.OrderListActivity;
import com.youmi.tt.activity.RegisterActivity;
import com.youmi.tt.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class MySelfFragment extends BaseFragment {


    private View view;

    public MySelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_my_self, container, false);
            ButterKnife.bind(this, view);



        }

        return view;
    }

    @OnClick({R.id.img_user,R.id.rl_unpay,R.id.rl_uncheck,R.id.rl_done,R.id.rl_address})
    public void onViewClick(View v){
        switch (v.getId()){
            case R.id.img_user:
                //跳转登录界面
                startActivity(RegisterActivity.class);
                break;
            case R.id.rl_unpay:
                startActivity(OrderListActivity.class);
                break;
            case R.id.rl_uncheck:
                startActivity(OrderListActivity.class);
                break;
            case R.id.rl_done:
                startActivity(OrderListActivity.class);
                break;
            case R.id.rl_address:
                startActivity(AddressListActivity.class);
                break;


        }
    }

}
