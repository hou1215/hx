package com.youmi.tt.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.entity.TestModel;
import com.youmi.tt.utils.CommonUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by hx on 2016/11/24.
 */
public class CategoryGoodsAdapter extends BaseAdapter<TestModel> {


    public CategoryGoodsAdapter(Activity context, List<TestModel> datas) {
        super(context, datas);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CategoryGoodsAdapter.ViewHolder(inflateView(R.layout.item_category, parent));
    }

    class ViewHolder extends BaseViewHolder{

        ImageView img_goods;
        TextView goods_title;
        TextView goods_price;
        TextView add;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);     //屏幕适配

            img_goods = fv(R.id.img_goods);
            goods_title = fv(R.id.goods_title);
            goods_price = fv(R.id.goods_price);
            add = fv(R.id.add);

        }

        @Override
        public void onBindData(TestModel testModel, int position) {
            setText(add,"+");
        }
    }
}
