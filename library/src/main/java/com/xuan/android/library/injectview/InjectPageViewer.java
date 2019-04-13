package com.xuan.android.library.injectview;

import android.app.Application;

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
}
