package com.xuan.android.library.life;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.xuan.android.library.AnyDoor;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :Activity生命周期观察者
 */
public class ActivityObserver implements Application.ActivityLifecycleCallbacks {

    private WeakReference<Activity> mActivity;

    public ActivityObserver() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (mActivity != null) {
            if (mActivity.get() != activity) {
                //执行了跳转
                //每次执行了页面跳转，则清理所有任务，暂时不支持跨页面
                AnyDoor.clear();
            }
        }
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
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
    public DialogFragment getCurDialogFragment() {
        if (mActivity == null) {
            return null;
        }
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing() || !(activity instanceof FragmentActivity)) {
            return null;
        }
        FragmentManager fragManager = ((FragmentActivity) mActivity.get())
                .getSupportFragmentManager();
        List<Fragment> fragments = fragManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof DialogFragment && fragment.isVisible()) {
                return (DialogFragment) fragment;
            }
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