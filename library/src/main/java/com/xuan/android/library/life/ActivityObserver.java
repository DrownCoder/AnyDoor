package com.xuan.android.library.life;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.xuan.android.library.injectview.InjectPageViewer;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :Activity观察者
 */
public class ActivityObserver implements Application.ActivityLifecycleCallbacks {
    /**
     * 用于检测当前APP是否运行于前台
     */
    private int appCount = 0;

    private WeakReference<Activity> mActivity;

    public ActivityObserver() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * 获取当前的前台Activity
     */
    @Nullable
    public Activity getCurActivity() {
        if (mActivity == null) {
            return null;
        }
        return mActivity.get();
    }

    /**
     * 获取当前页面的Fragment
     */
    @Nullable
    public Fragment getCurFragment() {
        if (mActivity == null) {
            return null;
        }
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing() || !(activity instanceof FragmentActivity)) {
            return null;
        }
        FragmentManager fragManager = ((FragmentActivity) mActivity.get())
                .getSupportFragmentManager();
        fragManager.getFragments();
        List<Fragment> fragments = fragManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    /**
     * 获取一个WindowManager对象
     *
     * @return 如果获取不到则抛出空指针异常
     */
    public WindowManager getWindowManager() {
        Activity activity = getCurActivity();
        if (activity != null) {
            // 如果使用的 WindowManager 对象不是当前 Activity 创建的，则会抛出异常
            // android.view.WindowManager$BadTokenException: Unable to add window -- token null
            // is not for an application
            return getWindowManagerObject(activity);
        }
        return null;
    }

    /**
     * 获取一个 WindowManager 对象
     */
    private static WindowManager getWindowManagerObject(Activity activity) {
        return ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE));
    }
}
