package com.xuan.android.library.model;

import android.os.SystemClock;

import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :the description of this file
 */
public class Task implements Comparable<Task> {
    public long startTime;
    public long duration;
    public IViewInjector viewInjector;
    public boolean singleLock = true;//同一时间只能弹出一个

    public Task(IViewInjector viewInjector) {
        recover(viewInjector);
    }

    public void recycle() {
        startTime = 0;
        duration = 0;
        viewInjector = null;
    }

    public void recover(IViewInjector viewInjector) {
        this.viewInjector = viewInjector;
        singleLock = true;
        startTime = SystemClock.uptimeMillis() + viewInjector.delay();
        duration = viewInjector.duration();
    }

    @Override
    public int compareTo(Task o) {
        return startTime > o.startTime ? 1 : -1;
    }
}
