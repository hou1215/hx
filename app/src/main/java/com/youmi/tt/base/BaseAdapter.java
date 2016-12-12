package com.youmi.tt.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.youmi.tt.utils.CommonUtils;
import com.youmi.tt.utils.ImageLoader;

import java.util.List;

/**
 * RecyclerView.Adapter 基类
 *
 * @Author dzl on 2016/10/31.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder> {

    public final String TAG = CommonUtils.getTag(this);

    public static final int VISIBLE = View.VISIBLE;
    public static final int GONE = View.GONE;
    public static final int INVISIBLE = View.INVISIBLE;

    protected int what;
    protected int position_select;

    protected List<T> datas;
    protected Activity context;

    // item 点击事件
    protected OnItemClickListener onItemClickListener;
    //item 长按事件
    protected OnItemLongClickListener onItemLongClickListener;

    // item 是否增加点击 (默认true)
    protected boolean isItemClick = true;

    public BaseAdapter(Activity context, List<T> datas) {
        super();
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public Activity getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.onBindData(datas.get(position), position);
        if (isItemClick) {

            holder.getDefaultClickView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                }
            });

            holder.getDefaultClickView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(holder.itemView, position);
                    }
                    return true;
                }
            });
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        protected <T extends View> T fv(int id, View... view) {
            if (view.length > 0) {
                return (T) view[0].findViewById(id);
            }
            return (T) itemView.findViewById(id);
        }

        public abstract void onBindData(T t, int position);

        public View getDefaultClickView() {
            return itemView;
        }
    }

    /**
     * 监听点击接口
     */
    public static interface OnItemClickListener {
        public void onItemClick(View itemView, int position, int... what);
    }

    /**
     * 监听长按点击接口
     */
    public static interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int position, int... what);
    }


    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getPositionSelect() {
        return position_select;
    }

    public void setPositionSelect(int position_select) {
        this.position_select = position_select;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public boolean isItemClick() {
        return isItemClick;
    }

    public void setItemClick(boolean isItemClick) {
        this.isItemClick = isItemClick;
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
    protected <T extends View> T fv(int id, View view) {
        return (T) view.findViewById(id);
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
}
