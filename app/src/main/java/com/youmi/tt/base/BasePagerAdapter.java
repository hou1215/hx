package com.youmi.tt.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.List;

/**
 * PagerAdapter 基类
 * 
 * @Author hx on 2016/10/31.
 *
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

	public static final int VISIBLE = View.VISIBLE;
	public static final int GONE = View.GONE;
	public static final int INVISIBLE = View.INVISIBLE;

	protected List<T> datas;
	protected Activity context;

	protected SparseArray<View> views;
	protected OnItemPagerClickListener onItemPagerClickListener;

	public BasePagerAdapter(Activity context, List<T> datas) {
		super();
		this.context = context;
		this.datas = datas;
		views = new SparseArray<>();
	}

	public List<T> getDatas() {
		return datas;
	}

	public T getItemObject(int position) {
		return datas.get(position);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View itemView = views.get(position);
		if (itemView == null) {
			itemView = onCreateView(container, position);
			views.put(position, itemView);
		}

		if (onItemPagerClickListener != null) {
			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (onItemPagerClickListener != null) {
						onItemPagerClickListener.onItemPagerClick(v, position);
					}
				}
			});
		}

		onBindView(itemView, position);
		container.addView(itemView);
		return itemView;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	protected abstract void onBindView(View itemView, int position);

	protected abstract View onCreateView(ViewGroup container, int position);

	public static interface OnItemPagerClickListener {
		void onItemPagerClick(View itemView, int position);
	}

	public OnItemPagerClickListener getOnItemPagerClickListener() {
		return onItemPagerClickListener;
	}

	public void setOnItemPagerClickListener(OnItemPagerClickListener onItemPagerClickListener) {
		this.onItemPagerClickListener = onItemPagerClickListener;
	}

}
