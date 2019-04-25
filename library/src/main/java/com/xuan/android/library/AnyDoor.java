package com.xuan.android.library;

import android.app.Application;

import com.xuan.android.library.core.EnergyProvider;
import com.xuan.android.library.injectview.InjectPageViewer;
import com.xuan.android.library.toast.ToastManager;
import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :任意门 - 全局任何地方弹toast和View注入
 */
public class AnyDoor {
    private volatile static AnyDoor instance;
    private EnergyProvider provider;

    public AnyDoor(Application application) {
        provider = new EnergyProvider(application);
    }

    public static AnyDoor init(Application application) {
        if (instance == null) {
            synchronized (AnyDoor.class) {
                if (instance == null) {
                    instance = new AnyDoor(application);
                }
            }
        }
        return instance;
    }

    public static AnyDoor getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("AnyDoor has not init in Application");
        }
        return instance;
    }

    /**
     * 获取配置
     */
    public static EnergyProvider provider() {
        return getInstance().provider;
    }

    /**
     * 显示Toast
     */
    public static void showToast(CharSequence toast) {
        ToastManager.showToast(toast);
    }

    /**
     * 添加View
     *
     * @param viewInjector 注入的View
     */
    public static void openDoor(IViewInjector viewInjector) {
        openDoor(viewInjector, true);
    }

    /**
     * 添加View
     *
     * @param viewInjector 注入的View
     * @param constrained  是否受任务队列的约束
     */
    public static void openDoor(IViewInjector viewInjector, boolean constrained) {
        InjectPageViewer.show(viewInjector, constrained);
    }

    /**
     * 移除注入的View
     *
     * @param viewInjector 注入的View，保证和添加的是同一实例
     */
    public static void closeDoor(IViewInjector viewInjector) {
        InjectPageViewer.dismiss(viewInjector);
    }

    /**
     * 移除正在显示的View
     */
    public static void cancel() {
        AnyDoor.provider().engine().cancelRunningTask();
    }

    /**
     * 移除所有任务的View，包括未执行的
     */
    public static void clear() {
        AnyDoor.provider().engine().cancelAllTask();
    }
}
