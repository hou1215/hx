package com.youmi.tt.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.interfacelistener.IRefreshTopMoreListener;
import com.youmi.tt.utils.ActivityUtil;
import com.youmi.tt.utils.CommonUtils;
import com.youmi.tt.utils.ImageLoader;
import com.youmi.tt.view.recyclerview.HeaderRefreshView;
import com.youmi.tt.view.recyclerview.RecyclerViewWrap;


/**
 * Created by hx on 2016/11/23.
 */

public abstract class BaseFragment extends Fragment
        implements View.OnClickListener, IRefreshTopMoreListener, BaseAdapter.OnItemClickListener {

    public final String TAG = CommonUtils.getTag(this);

    // ======== url请求列表 ========
    public static final int URL_LIST = 1;
    public static final int URL_LIST_CATEGORY1 = 1;
    public static final int URL_LIST_CATEGORY2 = 2;


    public static final int VISIBLE = View.VISIBLE;
    public static final int GONE = View.GONE;
    public static final int INVISIBLE = View.INVISIBLE;

    // ======== 网络请求加载模式 ========
    public static final int LOAD_AUTO = 1; // 加载方式 （自动1、顶部刷新2、加载更多3）
    public static final int LOAD_TOP = 2;
    public static final int LOAD_MORE = 3;

    // ======== 页次页码、是否是最后一页(最后一页，则禁用加载更多) ========
    protected int page;
    protected boolean is_last_page;

    protected Activity context;
    protected View main_layout;

    protected String frag_title; // 绑定标题
    protected int frag_index; // 绑定下标
    protected int frag_what; // 绑定类型

    protected LinearLayout loadview_ll; // 加载父布局
    protected TextView loadview_tv;

    public Activity getContext() {
        if (context == null) {
            context = getActivity();
        }
        return context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public void onError(int url_type, int load_type, String error) {

    }

    @Override
    public void onClick(View v) {

    }

    public String getTitle() {
        return frag_title;
    }

    public void setTitle(String title) {
        this.frag_title = title;
    }

    public int getIndex() {
        return frag_index;
    }

    public void setIndex(int index) {
        this.frag_index = index;
    }

    public int getWhat() {
        return frag_what;
    }

    public void setWhat(int what) {
        this.frag_what = what;
    }

    /**
     * 判断空
     */
    public boolean isEmpty(Object list) {
        return CommonUtils.isEmpty(list);
    }

    /**
     * toast
     */
    public void toast(CharSequence text) {
        CommonUtils.toast(getContext(), text);
    }

    /**
     * log 输出
     */
    public void log(String msg, String... tags) {
        CommonUtils.log(msg, TAG);
    }

    /**
     * print log 输出
     */
    public void println(Object text) {
        CommonUtils.println(text);
    }

    /**
     * 格式化
     */
    public String format(String format, Object... args) {
        return CommonUtils.format(format, args);
    }

    /**
     * 格式化N位小数
     * <p>
     * 默认保留2位
     */
    public String formatDouble(double number, int... n) {
        return CommonUtils.formatDouble(number, n);
    }

    /**
     * 设置view 可见性
     */
    public void setViewVisible(View view, boolean... isVisible) {
        CommonUtils.setViewVisible(view, isVisible);
    }

    /**
     * 设置点击监听
     */
    public void setOnClickListener(View view) {
        CommonUtils.setOnClickListener(view, this);
    }

    /**
     * 设置view 选中
     */
    public void setViewSelect(View view, boolean... isSelect) {
        CommonUtils.setViewSelect(view, isSelect);
    }

    /**
     * 设置 文本
     */
    public void setText(TextView view, CharSequence text) {
        CommonUtils.setText(view, text);
    }

    /**
     * 设置view 选中
     */
    public void setViewEnable(View view, boolean... isEnable) {
        CommonUtils.setViewEnable(view, isEnable);
    }



    /**
     * 创建dialog
     */
    public Dialog createDialog(int layout, int theme, boolean... cancel) {
        return CommonUtils.createDialog(getContext(), layout, theme, cancel);
    }

    /**
     * 创建dialog
     */
    public Dialog createDialog(View view, int theme, boolean... cancel) {
        return CommonUtils.createDialog(getContext(), view, theme, cancel);
    }

    /**
     * 加载 layout
     */
    public View inflateView(int layoutId, ViewGroup... root) {
        return CommonUtils.inflateView(getContext(), layoutId, root);
    }

    /**
     * 加载图片
     */
    public void loadImage(ImageView view, String url, int... resId) {
        ImageLoader.loadImage(view, url, resId);

    }

    //通过这个方法快速获取一个view，自动强制类型转换
    protected <T extends View> T fv(int id, View... view) {
        if (view.length > 0) {
            return (T) view[0].findViewById(id);

        } else if (main_layout != null) {
            return fv(id, main_layout);
        }
        return (T) getView().findViewById(id);
    }

    /**
     * 返回当前实例
     */
    public Activity self() {
        return getContext();
    }

    /**
     * 显示Toast
     */
    public void showToast(Object msg) {
        toast(String.valueOf(msg));
    }


    /**
     * 加载 尺寸
     */
    public float getDimen_(int resId) {
        return CommonUtils.getDimen_(getContext(), resId);
    }

    /**
     * 加载 drawable
     */
    public Drawable getDrawable_(int resId) {
        return CommonUtils.getDrawable_(getContext(), resId);
    }

    /**
     * 加载 color
     */
    public int getColor_(int resId) {
        return CommonUtils.getColor_(getContext(), resId);
    }

    /**
     * 设置 color
     * eg: 0xff00ff00 16进制
     */
    public static void setViewColor(View view, int color_hex) {
        CommonUtils.setViewColor(view, color_hex);
    }

    /**
     * 设置 color
     * eg: R.color|drawable.xx
     */
    public static void setViewColorRes(View view, int resId) {
        CommonUtils.setViewColorRes(view, resId);
    }

    public void startActivity(Class<?> cls){
        ActivityUtil.goToActivityFromRight2Left(context,cls);
    }

    @Override
    public void onLoadMore(View view) {

    }

    @Override
    public void onRefresh() {

    }

    /**
     * 设置 recyclerview 刷新
     */
    protected void setRefreshLister(RecyclerViewWrap recyclerview) {
        HeaderRefreshView header = new HeaderRefreshView(getContext());
        header.setLayoutParams(new RelativeLayout.LayoutParams(-1, 160));

        recyclerview.setRefreshHeaderView(header);

        //设置 刷新监听
        recyclerview.setOnRefreshListener(this);
        recyclerview.setOnLoadMoreListener(this);

        //禁止头部刷新  底部加载更多
        recyclerview.setLoadMoreEnabled(false);
        recyclerview.setRefreshEnabled(true);
    }

    @Override
    public void onItemClick(View itemView, int position, int... what) {

    }
}
