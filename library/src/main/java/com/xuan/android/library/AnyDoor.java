package com.xuan.android.library;

import android.app.Application;

import com.xuan.android.library.core.EnergyProvider;
import com.xuan.android.library.injectview.InjectPageViewer;
import com.xuan.android.library.toast.ToastManager;
import com.xuan.android.library.toast.helper.IToastStyle;
import com.xuan.android.library.toast.helper.ToastBuilder;
import com.xuan.android.library.ui.base.BaseViewInjector;
import com.xuan.android.library.ui.base.DialogViewInjector;
import com.xuan.android.library.ui.base.IViewInjector;
import com.xuan.android.library.ui.base.LifeViewInjector;


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
     * 显示自定义Toast
     */
    public static void showToast(String str, IToastStyle toastStyle) {
        ToastManager.showToast(str, toastStyle);
    }

    /**
     * 显示自定义Toast
     */
    public static void showToast(ToastBuilder builder) {
        ToastManager.showToast(builder);
    }

    /**
     * 取消Toast
     */
    public static void cancelToast() {
        ToastManager.cancel();
    }

    /**
     * 添加View
     *
     * @param viewInjector 注入的View
     *                     {@link BaseViewInjector} 基础的注入模版
     *                     {@link DialogViewInjector} Dialog形式的注入模版，不会自动取消
     *                     {@link LifeViewInjector} 感知生命周期的注入模版
     */
    public static void openDoor(IViewInjector viewInjector) {
        openDoor(viewInjector, true);
    }

    /**
     * 添加View
     *
     * @param viewInjector 注入的View
     *                     {@link BaseViewInjector} 基础的注入模版
     *                     {@link DialogViewInjector} Dialog形式的注入模版，不会自动取消
     *                     {@link LifeViewInjector} 感知生命周期的注入模版
     * @param async        是否受任务队列的约束
     */
    public static void openDoor(IViewInjector viewInjector, boolean async) {
        InjectPageViewer.show(viewInjector, async);
    }

    /**
     * 主动移除注入的View，不受duration限制，可以提前消失
     *
     * @param viewInjector 注入的View，保证和添加的是同一实例
     */
    public static void closeDoor(IViewInjector viewInjector) {
        closeDoor(viewInjector, true);
    }

    /**
     * 主动移除注入的View，不受duration限制，可以提前消失
     *
     * @param viewInjector 注入的View，保证和添加的是同一实例
     * @param async        是否是异步
     */
    public static void closeDoor(IViewInjector viewInjector, boolean async) {
        InjectPageViewer.dismiss(viewInjector, async);
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
