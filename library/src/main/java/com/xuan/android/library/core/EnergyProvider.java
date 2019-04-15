package com.xuan.android.library.core;

import android.app.Activity;
import android.app.Application;
import android.view.WindowManager;

import com.xuan.android.library.core.factory.ITaskFactory;
import com.xuan.android.library.core.strategy.ActivityInject;
import com.xuan.android.library.core.strategy.InjectStrategy;
import com.xuan.android.library.injectview.InjectPageViewer;
import com.xuan.android.library.life.ActivityObserver;
import com.xuan.android.library.toast.ToastManager;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :初始化参数
 */
public class EnergyProvider {
    private ActivityObserver activityObserver;
    private Application application;
    private InjectStrategy injectStrategy;
    private TaskEngine engine;
    private ITaskFactory factory;

    public EnergyProvider(Application application) {
        this.application = application;
        activityObserver = new ActivityObserver();
        application.registerActivityLifecycleCallbacks(activityObserver);
        ToastManager.init(application);
        InjectPageViewer.init(application);
        injectStrategy = new ActivityInject();
        engine = new TaskEngine();
        factory = new TaskFactory();
    }

    public WindowManager windowManager() {
        return activityObserver.getWindowManager();
    }

    public Activity activity() {
        return activityObserver.getCurActivity();
    }

    public Application application() {
        return application;
    }

    public InjectStrategy injector() {
        return injectStrategy;
    }

    public TaskEngine engine() {
        return engine;
    }

    public ITaskFactory factory() {
        return factory;
    }
}
