package com.xuan.android.library.model;

import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :注入View的任务
 */
public class Task implements Comparable<Task> {
    public long startTime;
    public long delay;
    public long duration;
    public IViewInjector viewInjector;
    public boolean asyncLock;//异步开关，默认是消息队列，异步

    public Task(IViewInjector viewInjector) {
        this.viewInjector = viewInjector;
        asyncLock = true;
    }

    @Override
    public int compareTo(Task o) {
        return startTime > o.startTime ? 1 : -1;
    }
}

