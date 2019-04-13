package com.xuan.android.library.core;

import android.app.Application;
import android.view.WindowManager;

import com.xuan.android.library.life.ActivityObserver;
import com.xuan.android.library.toast.ToastManager;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :初始化参数
 */
public class EnergyProvider {
    private ActivityObserver activityObserver;

    public EnergyProvider(Application application) {
        activityObserver = new ActivityObserver();
        application.registerActivityLifecycleCallbacks(activityObserver);
        ToastManager.init(application);
    }

    public WindowManager windowManager() {
        return activityObserver.getWindowManager();
    }
}
