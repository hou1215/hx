package com.youmi.tt.fragment;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.activity.GoodsDetailActivity;
import com.youmi.tt.activity.TipActivity;
import com.youmi.tt.adapter.ShopCarGoodsAdapter;
import com.youmi.tt.base.BaseAdapter;
import com.youmi.tt.base.BaseFragment;
import com.youmi.tt.entity.TestModel;
import com.youmi.tt.interfacelistener.OnNumberChangeListener;
import com.youmi.tt.request.ShopCarGoodsRequst;
import com.youmi.tt.utils.v7.RecyclerViewWrap;
import com.youmi.tt.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.youmi.tt.utils.ActivityUtil.goToActivityFromRight2Left;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class ShorCarFragment extends BaseFragment implements ShopCarGoodsRequst.IShopCarGoodView, OnNumberChangeListener {


    @Bind(R.id.recyclerview) RecyclerViewWrap recyclerview;
    @Bind(R.id.clear) TextView clearShopCar;
    @Bind(R.id.action) TextView action;
    @Bind(R.id.tab_title) TextView tab_title;
    @Bind(R.id.rl_shopcart) RelativeLayout rl_shopcart;
    @Bind(R.id.img_shopcar) ImageView img_shopcar;
    @Bind(R.id.loadview_ll) LinearLayout loadview_ll;

    private View view;
    private List<TestModel> goods;
    private ShopCarGoodsAdapter goodsAdapter;
    private LinearLayoutManager manager;
    private ShopCarGoodsRequst requst;
    private String url = "http://www.putaoji.com/apix/pubaTopic/lists?user_token=&uid=&api_version=1.0.0&ctype=4&row=10&page=1&search_uid=";


    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition = new float[2];
    // 路径测量
    private PathMeasure mPathMeasure;

    public ShorCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null){

            view = inflater.inflate(R.layout.fragment_shor_car, container, false);
            ButterKnife.bind(this, view);

            setText(tab_title,"购物车");

            goods = new ArrayList<>();
            goodsAdapter = new ShopCarGoodsAdapter(getActivity(),goods);

            manager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(manager);
            recyclerview.setHasFixedSize(true);

            // 使用 setIAdapter 不是setAdapter
            recyclerview.setAdapter(goodsAdapter);
            goodsAdapter.setListener(this);

            goodsAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position, int... what) {

                    goToActivityFromRight2Left(getActivity(), GoodsDetailActivity.class);
                }
            });

            reqData();


        }

        return view;
    }

    private void reqData() {
        if (requst == null) {
            requst = new ShopCarGoodsRequst(this,true);
        }
        requst.reqData(URL_LIST, LOAD_AUTO);
    }

    @OnClick(R.id.clear)
    public void clearShopCar(){
        goods.clear();
        recyclerview.getAdapter().notifyDataSetChanged();
        toast("购物车已清理");
    }

    @OnClick(R.id.action)
    public void gotoaccount(){
        goToActivityFromRight2Left(getActivity(), TipActivity.class);
    }



    @Override
    public void onSuccessGoods(int url_type, int load_type, List<TestModel> list) {
        goods.clear();
        if (list != null){
            goods.addAll(list);
            recyclerview.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public String getUrl(int url_type) {
        return url;
    }

    @Override
    public Map<String, String> getParams(int url_type, int load_type, Bundle bundle) {
        return null;
    }

    @Override
    public void onError(int url_type, int load_type, String error) {

    }

    @Override
    public void showLoadingUI(int url_type, int load_type) {
        if (url_type == URL_LIST) {
            // ... 显示加载UI
            if (load_type == LOAD_AUTO) {
                setViewVisible(loadview_ll, true);

            } else if (load_type == LOAD_TOP) {

            }
        }
    }

    @Override
    public void hideLoadingUI(int url_type, int load_type, boolean success) {
        if (url_type == URL_LIST) {
            if (load_type == LOAD_AUTO) {
                setViewVisible(loadview_ll);

            } else if (load_type == LOAD_TOP) {

            }
        }
    }

    /**
     * 购物车添加按钮
     */
    @Override
    public void addnumber(int position,ImageView img_g) {
        addGoodsToCart(img_g);
    }
    /**
     *购物车减少按钮
     */
    @Override
    public void minnumber(int position,ImageView img_g) {
        addGoodsToCart(img_g);
    }


    /**
     * 添加商品到购物车
     * @author leibing
     * @createTime 2016/09/28
     * @lastModify 2016/09/28
     * @param goodsImg 商品图标
     * @return
     */
    private void addGoodsToCart(ImageView goodsImg) {
        // 创造出执行动画的主题goodsImg（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
        final CircleImageView goods = new CircleImageView(getContext());
        //goods.setImageDrawable(goodsImg.getDrawable());
        goods.setBackgroundResource(R.mipmap.item);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        rl_shopcart.addView(goods, params);

        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl_shopcart.getLocationInWindow(parentLocation);

        // 得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        goodsImg.getLocationInWindow(startLoc);


        // 得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        img_shopcar.getLocationInWindow(endLoc);

        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + goodsImg.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + goodsImg.getHeight() / 2;

        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + img_shopcar.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        // 开始绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);


        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);

        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                // mCurrentPosition此时就是中间距离点的坐标值
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });

        // 开始执行动画
        valueAnimator.start();

        // 动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                // 把执行动画的商品图片从父布局中移除
                rl_shopcart.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

}
