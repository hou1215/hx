package com.youmi.tt.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.activity.AddressActivity;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.entity.Address;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import static com.youmi.tt.utils.ActivityUtil.goToActivityFromRight2Left;

/**
 * Created by hx on 2016/11/24.
 */
public class AddressListAdapter extends BaseAdapter<Address> {

    private final int ADDRESS       = 1;
    private final int ADD           = 2;

    public AddressListAdapter(Activity context, List<Address> datas) {
        super(context, datas);
    }


    @Override
    public int getItemViewType(int position) {
        if (position != datas.size() - 1)
            return ADDRESS;
        else
            return ADD;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != ADD)
            return new AddressListAdapter.ViewHolder(inflateView(R.layout.item_address, parent));
        else
            return new AddressListAdapter.Holder(inflateView(R.layout.item_add, parent));
    }

    class Holder extends BaseViewHolder{

        TextView add;

        public Holder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);     //屏幕适配

            add = fv(R.id.add);

        }

        @Override
        public void onBindData(Address address, int position) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToActivityFromRight2Left(context, AddressActivity.class);
                }
            });
        }
    }

    class ViewHolder extends BaseViewHolder{

        TextView name;
        TextView phone;
        TextView gendle;
        TextView address;
        TextView room;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);     //屏幕适配

            name = fv(R.id.name);
            phone = fv(R.id.phone);
            gendle = fv(R.id.gendle);
            address = fv(R.id.address);
            room = fv(R.id.room);

        }

        @Override
        public void onBindData(Address address, int position) {

        }
    }
}
