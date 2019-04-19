package com.xuan.android.library.model;

import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :the description of this file
 */
public class Task {
    public long delay;
    public long duration;
    public IViewInjector viewInjector;

    public void recycle() {
        delay = 0;
        duration = 0;
        viewInjector = null;
    }
}