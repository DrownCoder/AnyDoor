package com.xuan.android.library.core;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.xuan.android.library.core.factory.IInjectStrategyFactory;
import com.xuan.android.library.core.factory.ITaskFactory;
import com.xuan.android.library.core.factory.StrategyFactory;
import com.xuan.android.library.core.factory.TaskFactory;
import com.xuan.android.library.core.strategy.InjectStrategy;
import com.xuan.android.library.injectview.InjectPageViewer;
import com.xuan.android.library.life.ActivityObserver;
import com.xuan.android.library.toast.ToastManager;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :初始化参数，工具包装提供类
 */
public class EnergyProvider {
    private Application application;

    //activity生命周期观察者
    private ActivityObserver activityObserver;
    //任务中心，任务管理
    private TaskCenter engine;
    //注入View的管理类
    private InjectViewManager viewManager;
    //task工厂
    private ITaskFactory factory;
    //注入策略工厂
    private IInjectStrategyFactory strategyFactory;

    public EnergyProvider(Application application) {
        this.application = application;
        activityObserver = new ActivityObserver();
        application.registerActivityLifecycleCallbacks(activityObserver);
        ToastManager.init(application);
        InjectPageViewer.init(application);
        engine = new TaskCenter(application.getMainLooper());
        viewManager = new InjectViewManager();
        factory = new TaskFactory();
        strategyFactory = new StrategyFactory(activityObserver);
    }

    /**
     * 注入策略
     */
    InjectStrategy injector(IViewInjector viewInjector) {
        return strategyFactory.injectStrategy(viewInjector);
    }

    /**
     * WindowManager管理类
     */
    public WindowManager windowManager() {
        return activityObserver.getWindowManager();
    }

    /**
     * View管理类
     */
    public InjectViewManager viewManager() {
        return viewManager;
    }

    /**
     * 任务处理中心
     */
    public TaskCenter engine() {
        return engine;
    }

    /**
     * 任务创建工厂
     */
    public ITaskFactory factory() {
        return factory;
    }

    /**
     * 移除任务
     */
    public boolean remove(IViewInjector viewInjector) {
        return viewManager.remove(viewInjector);
    }

    /**
     * 当前显示的Activity
     */
    public Activity activity() {
        return activityObserver.getCurActivity();
    }

    /**
     * 当前显示的DialogFragment
     */
    public DialogFragment dialogFragment() {
        return activityObserver.getCurDialogFragment();
    }

    public Application application() {
        return application;
    }

    /**
     * 注入
     */
    public boolean inject(IViewInjector viewInjector, boolean isDirect) {
        return viewManager.inject(viewInjector, isDirect);
    }

    public void clear() {
        engine.cancelAllTask();
        viewManager.clear();
    }
}
