package com.xuan.android.library.core;

import android.util.Log;
import android.view.View;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.core.strategy.InjectStrategy;
import com.xuan.android.library.ui.IViewInjector;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xuan.android.library.AnyDoorConfig.TAG;

/**
 * Author : xuan.
 * Date : 2019/4/28.
 * Description :注入View的管理类
 */
public class InjectViewManager {
    private volatile WeakReference<View> taskView;//正在显示的View
    private Map<IViewInjector, WeakReference<View>> directViews;//不受任务队列显示的任务集合
    private InjectStrategy injectStrategy;

    InjectViewManager() {
        directViews = new ConcurrentHashMap<>();
    }

    /**
     * 注入View
     *
     * @param viewInjector 注入的View
     * @param isDirect     是否是直接注入，不受任务队列限制
     */
    boolean inject(IViewInjector viewInjector, boolean isDirect) {
        if (viewInjector == null) {
            return false;
        }
        //选择注入策略
        injectStrategy = AnyDoor.provider().injector(viewInjector);
        //创建View
        View view = viewInjector.injectView(AnyDoor.provider()
                .activity(), injectStrategy.parentView());
        if (checkViewUnValid(view)) {
            return false;
        }
        //缓存View
        if (isDirect) {
            //缓存View
            directViews.put(viewInjector, new WeakReference<>(view));
        } else {
            taskView = new WeakReference<>(view);
        }
        //注入
        injectStrategy.inject(view, viewInjector);
        return true;
    }

    /**
     * 校验View的合法性
     */
    private boolean checkViewUnValid(View view) {
        if (view == null) {
            return true;
        }
        if (view.getParent() != null) {
            Log.e(TAG, "Can't inject the view which already has parent!\n无法注入已经有父布局的View！，请检测使用LayoutInflater.inflate的方法的第三个参数是否为true，如果为true，默认会加入父布局，并且返回父布局！请设置为false");
            return true;
        }
        return false;
    }

    boolean remove(IViewInjector viewInjector) {
        WeakReference<View> directView = directViews.remove(viewInjector);
        if (directView != null && directView.get() != null) {
            //直接显示的隐藏逻辑
            View view = directView.get();
            if (view != null) {
                injectStrategy = AnyDoor.provider().injector(viewInjector);
                injectStrategy.remove(view, viewInjector);
            } else {
                //任务队列的隐藏逻辑
                removeTaskView(viewInjector);
                return true;
            }
        } else {
            //任务队列的隐藏逻辑
            removeTaskView(viewInjector);
            return true;
        }
        return false;
    }

    private void removeTaskView(IViewInjector viewInjector) {
        View view;
        if (taskView != null) {
            view = taskView.get();
            if (view != null) {
                injectStrategy.remove(view, viewInjector);
            }
        }
    }
}
