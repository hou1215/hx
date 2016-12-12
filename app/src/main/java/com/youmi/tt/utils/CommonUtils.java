package com.youmi.tt.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.youmi.tt.config.Config;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonUtils {

	public static final int VISIBLE = View.VISIBLE;
	public static final int GONE = View.GONE;
	public static final int INVISIBLE = View.INVISIBLE;

	/**
	 * 判断空
	 */
	public static String getTag(Object obj) {
		return obj.getClass().getSimpleName();
	}

	/**
	 * 判断空
	 */
	public static boolean isEmpty(Object list) {

		if (list == null) {
			return true;

		} else if (list instanceof List) {
			return ((List<?>) list).isEmpty();

		} else if (list instanceof CharSequence) {
			return ((CharSequence) list).length() == 0;

		} else if (list instanceof Map) {
			return ((Map<?, ?>) list).isEmpty();

		} else if (list instanceof Set) {
			return ((Set<?>) list).isEmpty();

		}

		return false;
	}

	/**
	 * toast
	 */
	public static void toast(Context context, CharSequence text) {
		Toast.makeText(context, (String) text,Toast.LENGTH_LONG).show();
	}

	/**
	 * log 输出
	 */
	public static void log(String msg, String... tags) {
		if (Config.BEBUG){
			Log.i(tags.length > 0 ? tags[0] : "log-i", msg);
		}
	}

	/**
	 * log 输出
	 */
	public static void println(Object text) {
		System.out.println(format("println : obj = %s", text));
	}

	/**
	 * 格式化
	 */
	public static String format(String format, Object... args) {
		return String.format(format, args);
	}

	/**
	 * 格式化N位小数
	 * 
	 * 默认保留2位
	 */
	public static String formatDouble(double number, int... n) {
		int length = n.length > 0 ? n[0] : 2;
		return format("%." + length + "f", number);
	}

	/**
	 * 设置view 可见性
	 */
	public static void setViewVisible(View view, boolean... isVisible) {

		if (view == null) {
			return;
		}

		int visible = isVisible.length > 0 && isVisible[0] ? VISIBLE : GONE;

		if (visible == view.getVisibility()) {
			return;
		}

		view.setVisibility(visible);
	}

	/**
	 * 设置点击监听
	 * 
	 * @param listener
	 */
	public static void setOnClickListener(View view, OnClickListener listener) {

		if (view == null) {
			return;
		}

		view.setOnClickListener(listener);
	}

	/**
	 * 设置view 选中
	 */
	public static void setViewSelect(View view, boolean... isSelect) {

		if (view == null) {
			return;
		}

		boolean select = isSelect.length > 0 && isSelect[0];
		view.setSelected(select);
	}

	/**
	 * 设置 文本
	 */
	public static void setText(TextView view, CharSequence text) {
		if (view == null) {
			return;
		}
		view.setText(text);
	}

	/**
	 * 设置view 选中
	 */
	public static void setViewEnable(View view, boolean... isEnable) {

		if (view == null) {
			return;
		}

		view.setEnabled(isEnable.length > 0 && isEnable[0]);
	}


	/**
	 * 创建dialog
	 */
	public static Dialog createDialog(Context context, int layout, int theme, boolean... cancel) {
		return createDialog(context, inflateView(context, layout), theme, cancel);
	}

	/**
	 * 创建dialog
	 */
	public static Dialog createDialog(Context context, View view, int theme, boolean... cancel) {

		Dialog dialog = new Dialog(context, theme);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(cancel.length > 0 ? cancel[0] : false);
		return dialog;

	}

	/**
	 * 加载 layout
	 */
	public static View inflateView(Context context, int layoutId, ViewGroup... root) {
		return LayoutInflater.from(context).inflate(layoutId, root.length > 0 ? root[0] : null, false);
	}

    /**
     * 加载 尺寸
     */
	public static float getDimen_(Context context, int resId) {
		return context.getResources().getDimension(resId);
	}

    /**
     * 加载 drawable
     */
	public static Drawable getDrawable_(Context context, int resId) {
		return context.getResources().getDrawable(resId);
	}

    /**
     * 加载 color
     */
	public static int getColor_(Context context, int resId) {
		return context.getResources().getColor(resId);
	}

    /**
     * 设置 color
     * eg: 0xff00ff00 16进制
     */
    public static void setViewColor(View view, int color_hex) {
        if (view == null) {
            return;
        }
        view.setBackgroundColor(color_hex);
    }

    /**
     * 设置 color
     * eg: R.color|drawable.xx
     */
    public static void setViewColorRes(View view, int resId) {
        if (view == null) {
            return;
        }
        view.setBackgroundResource(resId);
    }

    /**
     * findViewById
     */
	public static <T extends View> T fv(int id, View view) {
		return (T) view.findViewById(id);
	}

	/**
	 * 设置下划线
	 */
	public static void underline(TextView textView ){
		  textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//设置删除线
			textView.getPaint().setAntiAlias(true);//抗锯齿
	}
	/**
	 * 打电话
	 */
	public static void callup(Context context,String text ){

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		//url:统一资源定位符
		//uri:统一资源标示符（更广）
		intent.setData(Uri.parse("tel:" + text));
		//开启系统拨号器
		context.startActivity(intent);
	}

	/**
	 * 获取设备号
	 * @param context
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm == null || tm.getDeviceId() == null ? "" : tm.getDeviceId();
	}

}
