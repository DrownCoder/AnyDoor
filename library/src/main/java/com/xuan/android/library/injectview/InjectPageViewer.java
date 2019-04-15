package com.xuan.android.library.injectview;

import android.app.Application;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :无侵入式注入View给页面
 */
public class InjectPageViewer {

    private volatile static InjectPageViewer instance;

    public InjectPageViewer(Application application) {
        if (application == null) {
            return;
        }
    }

    public static InjectPageViewer init(Application application) {
        if (instance == null) {
            synchronized (InjectPageViewer.class) {
                if (instance == null) {
                    instance = new InjectPageViewer(application);
                }
            }
        }
        return instance;
    }

    public synchronized static void show(IViewInjector viewInjector) {
        if (viewInjector == null) {
            return;
        }
        AnyDoor.provider().engine().add(AnyDoor.provider().factory().create(viewInjector));
    }
}
