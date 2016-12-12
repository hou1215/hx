package com.youmi.tt.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * view 设置工具类
 * 
 * @author dzl 2016年3月11日
 */
public class ViewSettingUtil {

	public static final int G = View.GONE;
	public static final int V = View.VISIBLE;
	public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
	public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

	public static void setText(TextView v, CharSequence text) {
		if (v == null) {
			return;
		}
		v.setText(text);
	}

	public static void setText(TextView v, CharSequence text, int visibility) {
		if (v == null) {
			return;
		}

		v.setText(text);

		if (v.getVisibility() != visibility) {
			v.setVisibility(visibility);
		}

	}

	public static void setOnClickListener(View v, OnClickListener listener) {
		if (v == null) {
			return;
		}
		v.setOnClickListener(listener);
	}

	public static void setViewVisible(View v, int visibility) {
		if (v == null) {
			return;
		}

		if (v.getVisibility() == visibility) {
			return;
		}

		v.setVisibility(visibility);
	}

	public static void setImageViewResource(ImageView v, int resId) {
		if (v == null) {
			return;
		}
		v.setImageResource(resId);
	}

}
