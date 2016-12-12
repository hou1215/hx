package com.youmi.tt.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.youmi.tt.fragment.ShorCarFragment;

/**
 * fragment 管理工具
 *
 * @Author dzl on 2016/11/1.
 */
public class FragmentManagerUtil {

    /**
     * 切换 fragment
     */
    public static void setForegroundFragment(FragmentManager manager, Fragment fragment, int resId) {
        // 添加新的Fragment
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(resId, fragment);// 替换Fragment
        ft.commitAllowingStateLoss();
    }

}
