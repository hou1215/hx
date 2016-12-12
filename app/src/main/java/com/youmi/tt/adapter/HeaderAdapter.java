package com.youmi.tt.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youmi.tt.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hx on 2016/11/23.
 */
public class HeaderAdapter  extends PagerAdapter {

    private static final int UPTATE_VIEWPAGER = 0;
    private static final String LOG = "NEWS_LOG";
    private final int size;
    private final ViewPager viewPager;
    private final Context context;
    private List<ImageView> images = new ArrayList<>();
    private ImageView[] mBottomImages;//底部只是当前页面的小圆点



    public HeaderAdapter(Context context, final int size, ViewPager viewPager, LinearLayout indicator) {
        this.context = context;
        this.size = size;
        this.viewPager = viewPager;
        //创建底部指示位置的导航栏
        mBottomImages = new ImageView[size];

        for (int i = 0; i < size; i++) {
            ImageView image = (ImageView) LayoutInflater.from(context).inflate(R.layout.header_image,null);
            image.setBackgroundResource(R.mipmap.header);
            images.add(image);

            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.color.colorAccent);
            } else {
                imageView.setBackgroundResource(R.color.line_color);
            }

            mBottomImages[i] = imageView;
            //把指示作用的原点图片加入底部的视图中
            indicator.addView(mBottomImages[i]);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //图片左右滑动时候，将当前页的圆点图片设为选中状态
            @Override
            public void onPageSelected(int index) {
                index %= images.size();
                // 一定几个图片，几个圆点，但注意是从0开始的
                int total = mBottomImages.length;
                for (int j = 0; j < total; j++) {
                    if (j == index) {
                        mBottomImages[j].setBackgroundResource(R.color.colorAccent);
                    } else {
                        mBottomImages[j].setBackgroundResource(R.color.line_color);
                    }
                }
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //对Viewpager页号求模去除View列表中要显示的项
        position %= images.size();
        if (position<0) {
            position = images.size() + position;
        }
        ImageView view = images.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。

        ViewParent viewParent = view.getParent();
        if (viewParent!=null){
            ViewGroup parent = (ViewGroup)viewParent;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
}
