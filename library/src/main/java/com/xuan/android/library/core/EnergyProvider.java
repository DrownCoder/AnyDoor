package com.xuan.android.library.core;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xuan.android.library.core.factory.ITaskFactory;
import com.xuan.android.library.core.factory.TaskFactory;
import com.xuan.android.library.core.strategy.ActivityInject;
import com.xuan.android.library.core.strategy.DialogFragmentInject;
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
    private TaskCenter engine;
    private ITaskFactory factory;

    public EnergyProvider(Application application) {
        this.application = application;
        activityObserver = new ActivityObserver();
        application.registerActivityLifecycleCallbacks(activityObserver);
        ToastManager.init(application);
        InjectPageViewer.init(application);
        injectStrategy = new ActivityInject();
        engine = new TaskCenter(application.getMainLooper());
        factory = new TaskFactory();
    }

    public WindowManager windowManager() {
        return activityObserver.getWindowManager();
    }

    public Activity activity() {
        return activityObserver.getCurActivity();
    }

    public Fragment fragment() {
        return activityObserver.getCurFragment();
    }

    public Application application() {
        return application;
    }

    public InjectStrategy injector() {
        if (activityObserver.getCurFragment() instanceof DialogFragment && checkDialogValid
                ((DialogFragment) activityObserver.getCurFragment())) {
            return new DialogFragmentInject();
        }
        return injectStrategy;
    }

    public TaskCenter engine() {
        return engine;
    }

    public ITaskFactory factory() {
        return factory;
    }


    /**
     * 校验DialogFragment符合注入要求:是一个全屏幕的DialogFragment
     * 否则不能向DialogFragment注入，因为不是全屏的，那么注入的布局会被缩放
     *
     * @param dialogFragment 当前的DialogFragment
     * @return true-可以注入DialogFragment false-还是注入Activity
     */
    private boolean checkDialogValid(DialogFragment dialogFragment) {
        if (dialogFragment.getDialog().getWindow() == null) {
            return false;
        }
        WindowManager.LayoutParams windowParams = dialogFragment.getDialog().getWindow()
                .getAttributes();
        if (windowParams == null || windowParams.width != ViewGroup.LayoutParams.MATCH_PARENT ||
                windowParams.height != ViewGroup.LayoutParams.MATCH_PARENT) {
            return false;
        }
        return true;
    }
}
