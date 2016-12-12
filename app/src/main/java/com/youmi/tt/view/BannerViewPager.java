package com.youmi.tt.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.youmi.tt.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 轮播图
 * <p>
 * <pre>
 * < Banner
 * 		 xmlns:app="http://schemas.android.com/apk/res-auto"
 *       android:id="@+id/banner"
 *       android:layout_width="match_parent"
 *       android:layout_height="200dp"
 *       app:default_image="@drawable/default"
 *       app:indicator_drawable_selected="@drawable/selected"
 *       app:indicator_drawable_unselected="@drawable/unselected"
 *       app:indicator_height="8dp"
 *       app:indicator_margin="5dp"
 *       app:indicator_width="8dp" />
 *
 *       //显示圆形指示器
 *       banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
 *       banner.setImages(images, new Banner.OnLoadImageListener() {
 *          &#64;Override
 *          public void OnLoadImage(ImageView view, Object url) {
 *             //自定义图片加载
 *
 *      });
 *
 * </pre>
 *
 * @author dzl 2016年11月11日
 */
public class BannerViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {
    public static final int NOT_INDICATOR = 0;
    public static final int CIRCLE_INDICATOR = 1;
    public static final int NUM_INDICATOR = 2;
    public static final int NUM_INDICATOR_TITLE = 3;
    public static final int CIRCLE_INDICATOR_TITLE = 4;
    public static final int CIRCLE_INDICATOR_TITLE_INSIDE = 5;
    public static final int LEFT_ = 5;
    public static final int CENTER = 6;
    public static final int RIGHT_ = 7;

    public static final int INDICATOR_SIZE = 8;
    public static final int PADDING_SIZE = 5;
    public static final int TIME = 5000;
    public static final boolean IS_AUTO_PLAY = true;

    public String tag = "banner";
    private int mIndicatorMargin = PADDING_SIZE;
    private int mIndicatorWidth = INDICATOR_SIZE;
    private int mIndicatorHeight = INDICATOR_SIZE;
    private int bannerStyle = NOT_INDICATOR;
    private int delayTime = TIME;
    private boolean isAutoPlay = IS_AUTO_PLAY;
    private int mIndicatorSelectedResId = R.drawable.banner_gray;
    private int mIndicatorUnselectedResId = R.drawable.banner_white;
    private int defaultImage = -1;
    private int count;
    private int currentItem;
    private int gravity = -1;
    private int lastPosition = 1;
    private List<ImageView> imageViews;
    private List<ImageView> indicatorImages;
    private ViewPager viewPager;
    private LinearLayout indicator, indicatorInside;
    private Handler handler = new Handler();
    private OnBannerClickListener listener;
    private OnLoadImageListener imageListener;
    private String[] titles;
    private TextView bannerTitle, numIndicatorInside, numIndicator;
    private BannerPagerAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private Context context;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        imageViews = new ArrayList<>();
        indicatorImages = new ArrayList<>();
        initView(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.banner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.banner_indicator_width, INDICATOR_SIZE);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.banner_indicator_height, INDICATOR_SIZE);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.banner_indicator_margin, PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.banner_indicator_drawable_selected,
                R.drawable.banner_gray);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.banner_indicator_drawable_unselected,
                R.drawable.banner_white);
        defaultImage = typedArray.getResourceId(R.styleable.banner_default_image, defaultImage);
        delayTime = typedArray.getDimensionPixelSize(R.styleable.banner_delay_time, TIME);
        isAutoPlay = typedArray.getBoolean(R.styleable.banner_is_auto_play, IS_AUTO_PLAY);
        typedArray.recycle();
    }

    private void initView(Context context, AttributeSet attrs) {

        createView(context);
        handleTypedArray(context, attrs);
    }

    private void createView(Context context) {
        removeAllViews();
        imageViews.clear();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        indicatorInside = (LinearLayout) view.findViewById(R.id.indicatorInside);
        bannerTitle = (TextView) view.findViewById(R.id.bannerTitle);
        numIndicator = (TextView) view.findViewById(R.id.numIndicator);
        numIndicatorInside = (TextView) view.findViewById(R.id.numIndicatorInside);
    }

    public void isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @SuppressLint("RtlHardcoded")
    public void setIndicatorGravity(int type) {
        switch (type) {
            case LEFT_:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case RIGHT_:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
    }

    public void setBannerTitleList(List<String> titles) {
        setBannerTitle(titles.toArray(new String[titles.size()]));
    }

    public void setBannerTitle(String[] titles) {
        this.titles = titles;
        if (bannerStyle == CIRCLE_INDICATOR_TITLE || bannerStyle == NUM_INDICATOR_TITLE
                || bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE) {
            if (titles != null && titles.length > 0) {
                bannerTitle.setText(titles[0]);
                bannerTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setBannerStyle(int bannerStyle) {
        this.bannerStyle = bannerStyle;
        switch (bannerStyle) {
            case CIRCLE_INDICATOR:
                indicator.setVisibility(View.VISIBLE);
                break;
            case NUM_INDICATOR:
                numIndicator.setVisibility(View.VISIBLE);
                break;
            case NUM_INDICATOR_TITLE:
                numIndicatorInside.setVisibility(View.VISIBLE);
                break;
            case CIRCLE_INDICATOR_TITLE:
                indicator.setVisibility(View.VISIBLE);
                break;
            case CIRCLE_INDICATOR_TITLE_INSIDE:
                indicatorInside.setVisibility(VISIBLE);
                break;
        }
    }

    public void setImages(Object[] imagesUrl) {
        //createView(context);
        setImageArray(imagesUrl, imageListener);
    }

    public void setImages(Object[] imagesUrl, OnLoadImageListener imageListener) {
        //createView(context);
        setImageArray(imagesUrl, imageListener);
    }

    public void setImages(List<?> imagesUrl) {
        //createView(context);
        setImageList(imagesUrl, imageListener);
    }

    public void setImages(int images) {
        //createView(context);
        setImageList(images);
    }

    public void setImages(List<?> imagesUrl, OnLoadImageListener imageListener) {
        //createView(context);
        setImageList(imagesUrl, imageListener);
    }


    private void setImageArray(Object[] imagesUrl, OnLoadImageListener imageListener) {
        if (imagesUrl == null || imagesUrl.length <= 0) {
            Log.e(tag, "Please set the images data.");
            return;
        }

        imageViews.clear();
        count = imagesUrl.length;
        if (bannerStyle == CIRCLE_INDICATOR || bannerStyle == CIRCLE_INDICATOR_TITLE
                || bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (bannerStyle == NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.FIT_XY);
            Object url = null;
            if (i == 0) {
                url = imagesUrl[count - 1];
            } else if (i == count + 1) {
                url = imagesUrl[0];
            } else {
                url = imagesUrl[i - 1];
            }
            imageViews.add(iv);
            if (imageListener != null) {
                imageListener.OnLoadImage(iv, url);
            }
        }
        setData();
    }

    private void setImageList(int images){
        if (images <= 0) {
            Log.e(tag, "Please set the images data.");
            return;
        }
        imageViews.clear();
        count = images;
        if (bannerStyle == CIRCLE_INDICATOR || bannerStyle == CIRCLE_INDICATOR_TITLE
                || bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (bannerStyle == NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setBackgroundResource(R.mipmap.header);
            Object url = null;

            imageViews.add(iv);

        }
        setData();

    }

    private void setImageList(List<?> imagesUrl, OnLoadImageListener imageListener) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            Log.e(tag, "Please set the images data.");
            return;
        }
        imageViews.clear();
        count = imagesUrl.size();
        if (bannerStyle == CIRCLE_INDICATOR || bannerStyle == CIRCLE_INDICATOR_TITLE
                || bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (bannerStyle == NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.FIT_XY);
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViews.add(iv);
            if (imageListener != null) {
                imageListener.OnLoadImage(iv, url);
            }
        }
        setData();
    }

    private void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            if (bannerStyle == CIRCLE_INDICATOR || bannerStyle == CIRCLE_INDICATOR_TITLE)
                indicator.addView(imageView, params);
            else if (bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE)
                indicatorInside.addView(imageView, params);
        }
    }

    private void setData() {
        currentItem = 1;
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(this);
        // viewPager.addOnPageChangeListener(this);
        if (gravity != -1)
            indicator.setGravity(gravity);
        if (isAutoPlay)
            startAutoPlay();
    }

    private void startAutoPlay() {
        if (count > 1) {
            handler.removeCallbacks(task);
            handler.postDelayed(task, delayTime);
        }
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                } else {
                    viewPager.setCurrentItem(currentItem);
                }
            }
            handler.postDelayed(task, delayTime);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (count > 1) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isAutoPlay(false);
//				Log.i(tag, "--" + isAutoPlay);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isAutoPlay(true);
//				Log.i(tag, "--" + isAutoPlay);
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            final ImageView view = imageViews.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnBannerClick(v, position);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:
                isAutoPlay = false;
                break;
            case 2:
                isAutoPlay = true;
                break;
            case 0:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (viewPager.getCurrentItem() == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                currentItem = viewPager.getCurrentItem();
                isAutoPlay = true;
                break;
        }
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (bannerStyle == CIRCLE_INDICATOR || bannerStyle == CIRCLE_INDICATOR_TITLE
                || bannerStyle == CIRCLE_INDICATOR_TITLE_INSIDE) {
            indicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }
        if (position == 0)
            position = 1;
        switch (bannerStyle) {
            case CIRCLE_INDICATOR:
                break;
            case NUM_INDICATOR:
                if (position > count)
                    position = count;
                numIndicator.setText(position + "/" + count);
                break;
            case NUM_INDICATOR_TITLE:
                if (position > count)
                    position = count;
                numIndicatorInside.setText(position + "/" + count);
                if (titles != null && titles.length > 0) {
                    if (position > titles.length)
                        position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
            case CIRCLE_INDICATOR_TITLE:
                if (titles != null && titles.length > 0) {
                    if (position > titles.length)
                        position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
            case CIRCLE_INDICATOR_TITLE_INSIDE:
                if (titles != null && titles.length > 0) {
                    if (position > titles.length)
                        position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
        }

    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener = listener;
    }

    public void setOnBannerImageListener(OnLoadImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public interface OnBannerClickListener {
        void OnBannerClick(View view, int position);
    }

    public interface OnLoadImageListener {
        void OnLoadImage(ImageView view, Object url);
    }
}
