package com.youmi.tt.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youmi.tt.utils.CommonUtils;


/**
 * @Author dzl on 2016/7/14.
 */
public abstract class BaseRelativeLayout extends RelativeLayout {

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    public BaseRelativeLayout(Context context) {
        this(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected abstract void init(Context context);

    public void setText(TextView view, CharSequence text) {
        CommonUtils.setText(view, text);
    }

    /**
     * 设置view 可见性
     */
    public void setViewVisible(View view, boolean... isVisible) {
        CommonUtils.setViewVisible(view, isVisible);
    }
}
