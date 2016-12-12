package com.youmi.tt.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.entity.TestModel;
import com.youmi.tt.interfacelistener.OnNumberChangeListener;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by hx on 2016/11/24.
 */
public class ShopCarGoodsAdapter extends BaseAdapter<TestModel> {

    private OnNumberChangeListener listener;

    public void setListener(OnNumberChangeListener listener) {
        this.listener = listener;
    }


    public ShopCarGoodsAdapter(Activity context, List<TestModel> datas) {
        super(context, datas);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ShopCarGoodsAdapter.ViewHolder(inflateView(R.layout.item_shopcar, parent));
    }

    class ViewHolder extends BaseViewHolder {

        ImageView img_goods;
        ImageView img_center;
        TextView goods_title;
        TextView goods_price;
        TextView add, minu, count;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);     //屏幕适配

            img_goods = fv(R.id.img_goods);
            img_center = fv(R.id.img_center);
            goods_title = fv(R.id.goods_title);
            goods_price = fv(R.id.goods_price);
            add = fv(R.id.add);
            minu = fv(R.id.miun);
            count = fv(R.id.count);

        }

        @Override
        public void onBindData(TestModel testModel, final int position) {

            setText(add,"+");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.addnumber(position,img_goods);
                }
            });
            minu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.minnumber(position,img_goods);
                }
            });

        }


    }
}
