package com.youmi.tt.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youmi.tt.interfacelistener.IOnActivityDispatchListener;
import com.youmi.tt.interfacelistener.IOnFragmentDispatchListener;


/**
 * 网络的延迟加载fragment
 * 
 * 同时添加通信activity接口
 * 
 * @Author dzl on 2016/10/31.
 *
 */
public abstract class BaseLazyFragment extends BaseFragment implements IOnFragmentDispatchListener {

	// 默认false 未加载
	private boolean is_network_load = false;
	protected IOnActivityDispatchListener onActivityListener;// 交互activity的通信接口

	@Override
	public final void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		onFirstLazyLoad(null);
		onSetUserVisibleHint(isVisibleToUser);
		// fragment 执行顺序
		// 1. setUserVisibleHint( true ) ->生命周期函数
		// 2. onCreateView ->生命周期函数

		// 3. onFirstLazyLoad
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		initActivityListener();
		View view = createView(inflater, container, savedInstanceState);
		onFirstLazyLoad(view);
		return view;
	}

	/**
	 * 初始化 Activity handler
	 */
	protected void initActivityListener() {
		if (onActivityListener == null) {
			if (getActivity() instanceof IOnActivityDispatchListener) {
				onActivityListener = (IOnActivityDispatchListener) getActivity();
			}
		}
	}

	/**
	 * 重写此方法， 可实现网络的延迟加载
	 */
	public abstract void onNetworkLazyLoad();

	/**
	 * 对应 onCreateView 返回fragment内容view
	 */
	protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/**
	 * 判断是否是第一次网络的延迟加载
	 */
	protected final void onFirstLazyLoad(View v) {

		if (v == null) {
			// getView 是在 onCreateView执行完后 才有值
			v = getView();
		}

		// 只加载一次、且此fragment可见 、且已createView
		if (is_network_load || !getUserVisibleHint() || v == null) {
			return;
		}

		is_network_load = true;
		onNetworkLazyLoad();
	}

	public boolean is_network_load() {
		return is_network_load;
	}

	/**
	 * 对setUserVisibleHint的补充
	 */
	public void onSetUserVisibleHint(boolean isVisibleToUser) {

	}

	@Override
	public Object onDispatchData(int what, Object... obj) {
		return null;
	}


	/**
	 * 快速跳转页面
	 *
	 * @param clz
	 */
	public void skipPage(Class<? extends Activity> clz) {
		startActivity(new Intent(getActivity(), clz));
	}

}
