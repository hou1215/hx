package com.youmi.tt.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.activity.GoodsDetailActivity;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.entity.TestModel;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import static com.youmi.tt.utils.ActivityUtil.goToActivityFromRight2Left;


/**
 * Created by hx on 2016/11/23.
 */
public class HomeGoodsAdapter extends BaseAdapter<TestModel> {



    public HomeGoodsAdapter(Activity context, List<TestModel> datas) {
        super(context, datas);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new  HomeGoodsAdapter.ViewHoler(inflateView(R.layout.item_home_goods, parent));
    }



    class ViewHoler extends BaseViewHolder {

        AutoRelativeLayout rl_good01, rl_good02, rl_good03;
        ImageView img_01, img_02, img_03;
        TextView title01, title02, title03;
        TextView price01, price02, price03;

        public ViewHoler(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);     //屏幕适配

            rl_good01 = fv(R.id.rl_good01);
            rl_good02 = fv(R.id.rl_good02);
            rl_good03 = fv(R.id.rl_good03);
            img_01= fv(R.id.img_01);
            img_02 = fv(R.id.img_02);
            img_03 = fv(R.id.img_03);
            title01 = fv(R.id.title01);
            title02 = fv(R.id.title02);
            title03 = fv(R.id.title03);
            price01 = fv(R.id.price01);
            price02 = fv(R.id.price02);
            price03 = fv(R.id.price03);

        }

        @Override
        public void onBindData(TestModel testModel, int position) {

            //loadImage(img_02,testModel.getCover_image());
            rl_good01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToActivityFromRight2Left(context, GoodsDetailActivity.class);
                }
            });
            rl_good02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToActivityFromRight2Left(context, GoodsDetailActivity.class);
                }
            });
            rl_good03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToActivityFromRight2Left(context, GoodsDetailActivity.class);
                }
            });

        }
    }

}
