package com.youmi.tt.utils.v7;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youmi.tt.R;
import com.youmi.tt.base.BaseRelativeLayout;
import com.youmi.tt.utils.CommonUtils;
import com.youmi.tt.utils.v7.ProgressImageView;


/**
 * 底部View 用于展示数据加载情况
 *
 * @author dzl 2016年3月1日
 */
public class FooterStatusView extends BaseRelativeLayout {

    public static final int FOOTER_EMPTY = 1; // empty
    public static final int FOOTER_LOADING = 2; // loading
    public static final int FOOTER_END = 4; // end
    public static final int FOOTER_ERROR = 8; // 加载失败
    public static final int FOOTER_PLACEHOLDER = 16; // 占位

    public RelativeLayout footer_rl_content;
    public ImageView footer_img_empty;

    public LinearLayout footer_ll_empty;
    public TextView footer_tv_empty;

    public LinearLayout footer_ll_end;
    public TextView footer_tv_end;

    public LinearLayout footer_ll_error;
    public TextView footer_tv_error;

    public LinearLayout footer_ll_loading;
    public ProgressImageView footer_img_loading;
    public TextView footer_tv_loading;

    public RelativeLayout footer_ll_placeholder;

    private OnFooterViewClickListener onFooterViewClickListener;

    public FooterStatusView(Context context) {
        super(context);
    }

    public FooterStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {

        footer_rl_content = (RelativeLayout) CommonUtils.inflateView(context, R.layout.layout_loadmore, this);

        footer_img_empty = CommonUtils.fv(R.id.footer_img_empty, footer_rl_content);
        footer_img_loading = CommonUtils.fv(R.id.footer_img_loading, footer_rl_content);

        footer_ll_empty = CommonUtils.fv(R.id.footer_ll_empty, footer_rl_content);
        footer_ll_end = CommonUtils.fv(R.id.footer_ll_end, footer_rl_content);
        footer_ll_loading = CommonUtils.fv(R.id.footer_ll_loading, footer_rl_content);

        footer_ll_placeholder = CommonUtils.fv(R.id.footer_ll_placeholder, footer_rl_content);
        footer_tv_empty = CommonUtils.fv(R.id.footer_tv_empty, footer_rl_content);
        footer_tv_end = CommonUtils.fv(R.id.footer_tv_end, footer_rl_content);
        footer_tv_loading = CommonUtils.fv(R.id.footer_tv_loading, footer_rl_content);

        footer_ll_error = CommonUtils.fv(R.id.footer_ll_error, footer_rl_content);
        footer_tv_error = CommonUtils.fv(R.id.footer_tv_error, footer_rl_content);

        addView(footer_rl_content);

        footer_ll_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFooterViewClickListener != null) {
                    onFooterViewClickListener.onFooterEmptyClick(v);
                }
            }
        });

        footer_ll_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFooterViewClickListener != null) {
                    onFooterViewClickListener.onFooterEndClick(v);
                }
            }
        });

        footer_ll_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFooterViewClickListener != null) {
                    onFooterViewClickListener.onFooterErrorClick(v);
                }
            }
        });


    }

    public interface OnFooterViewClickListener {

        public void onFooterEmptyClick(View v);

        public void onFooterEndClick(View v);

        public void onFooterErrorClick(View v);

    }

    public OnFooterViewClickListener getOnFooterViewClickListener() {
        return onFooterViewClickListener;
    }

    public void setOnFooterViewClickListener(OnFooterViewClickListener onFooterViewClickListener) {
        this.onFooterViewClickListener = onFooterViewClickListener;
    }

    /**
     * 状态切换，更新view
     */
    public void setViewStatusChange(int type, CharSequence end_error_msg) {

        switch (type) {

            case FOOTER_EMPTY:
                setViewVisible(footer_ll_empty, true);
                setViewVisible(footer_ll_end);
                setViewVisible(footer_ll_loading);
                setViewVisible(footer_ll_error);
                setViewVisible(footer_ll_placeholder);
                setText(footer_tv_empty, end_error_msg);
                break;

            case FOOTER_LOADING:

                setViewVisible(footer_ll_empty);
                setViewVisible(footer_ll_loading, true);
                setViewVisible(footer_ll_end);
                setViewVisible(footer_ll_error);
                setViewVisible(footer_ll_placeholder);

                if (end_error_msg != null) {
                    setText(footer_tv_loading, end_error_msg);
                }
                break;

            case FOOTER_END:
                setViewVisible(footer_ll_empty);
                setViewVisible(footer_ll_loading);
                setViewVisible(footer_ll_end, true);
                setViewVisible(footer_ll_error);
                setViewVisible(footer_ll_placeholder);
                if (end_error_msg != null) {
                    setText(footer_tv_end, end_error_msg);
                }
                break;

            case FOOTER_ERROR:
                setViewVisible(footer_ll_empty);
                setViewVisible(footer_ll_loading);
                setViewVisible(footer_ll_end);
                setViewVisible(footer_ll_error, true);
                setViewVisible(footer_ll_placeholder);

                if (end_error_msg != null) {
                    setText(footer_tv_error, end_error_msg);
                }
                break;

            case FOOTER_PLACEHOLDER:
                setViewVisible(footer_ll_empty);
                setViewVisible(footer_ll_loading);
                setViewVisible(footer_ll_end);
                setViewVisible(footer_ll_error);
                setViewVisible(footer_ll_placeholder, true);

                break;
        }

    }


}
