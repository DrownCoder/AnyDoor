package com.xuan.android.library.model;

import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :the description of this file
 */
public class Task implements Comparable<Task> {
    public long startTime;
    public long delay;
    public long duration;
    public IViewInjector viewInjector;
    public boolean singleLock = true;//同一时间只能弹出一个

    public Task(IViewInjector viewInjector) {
        init(viewInjector);
    }

    public void init(IViewInjector viewInjector) {
        this.viewInjector = viewInjector;
        singleLock = true;
    }

    @Override
    public int compareTo(Task o) {
        return startTime > o.startTime ? 1 : -1;
    }
}
