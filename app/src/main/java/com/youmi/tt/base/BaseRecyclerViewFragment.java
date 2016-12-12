package com.youmi.tt.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.youmi.tt.utils.v7.FooterStatusView;
import com.youmi.tt.utils.v7.RecyclerViewWrap;

/**
 * @Author dzl on 2016/11/2.
 */
public abstract class BaseRecyclerViewFragment extends BaseLazyFragment
        implements FooterStatusView.OnFooterViewClickListener {

    protected RecyclerViewWrap recyclerview;

    protected FooterStatusView footer_status_view;
    protected int footer_params_width = 0; // 底部宽度
    protected int footer_params_height = 0; // 底部高度

    /**
     * 添加 底部(空、loadmore、end、error、占位)
     */
    protected void addFooterView(int type, int width, int height, String end_error_msg) {
        addFooterView(recyclerview, type, width, height, end_error_msg);
    }

    /**
     * 添加 底部(空、loadmore、end、error、占位)
     */
    protected void addFooterView(RecyclerViewWrap recyclerview, int type, int width, int height, String end_error_msg) {

        if (height == FooterStatusView.MATCH_PARENT) {
            height = recyclerview.getMeasuredHeight();

            if (height < FooterStatusView.WRAP_CONTENT) {
                height = FooterStatusView.MATCH_PARENT;
            }
        }

        if (footer_status_view == null) {
            // 初始化
            footer_status_view = new FooterStatusView(getContext());

            footer_params_width = width;
            footer_params_height = height;

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, height);
            footer_status_view.setLayoutParams(params);

            // 添加进入 recyclerview
            recyclerview.addFooterView(footer_status_view);

            // 设置监听
            setFooterStatusViewListener();

        }

        if (width != footer_params_width || height != footer_params_height) {
            // 比较宽高，重新布局

            footer_params_width = width;
            footer_params_height = height;

            ViewGroup.LayoutParams params = footer_status_view.getLayoutParams();
            params.width = width;
            params.height = height;

            footer_status_view.setLayoutParams(params);

        }

        footer_status_view.setViewStatusChange(type, end_error_msg);
    }

    protected void setFooterStatusViewListener() {
        footer_status_view.setOnFooterViewClickListener(this);
    }

    @Override
    public void onFooterEmptyClick(View v) {
        // reqData(URL_LIST, LOAD_TOP);
    }

    @Override
    public void onFooterEndClick(View v) {

    }

    @Override
    public void onFooterErrorClick(View v) {
        // presenter.reqDataReTry();
    }

}
