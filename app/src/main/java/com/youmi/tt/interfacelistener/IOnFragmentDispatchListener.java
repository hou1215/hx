package com.youmi.tt.interfacelistener;

/**
 * fragment 通信接口
 * 
 * @Author dzl on 2016/10/31.
 *
 */
public interface IOnFragmentDispatchListener {

	/**
	 * 交互数据
	 */
	public Object onDispatchData(int what, Object... obj);

}
