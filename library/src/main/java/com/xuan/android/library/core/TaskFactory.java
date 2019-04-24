package com.xuan.android.library.core;

import android.os.SystemClock;

import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.core.factory.ITaskFactory;
import com.xuan.android.library.model.Task;
import com.xuan.android.library.ui.IViewInjector;


/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务构建
 */
public class TaskFactory implements ITaskFactory {

    @Override
    public Task create(IViewInjector viewInjector) {
        Task task = new Task(viewInjector);
        task.delay = viewInjector.delay();
        task.startTime = SystemClock.uptimeMillis() + task.delay;
        task.duration = viewInjector.duration();
        if (task.singleLock) {
            //如果弹窗大于显示时长限制，则进化为特殊弹窗，不受任务队列限制
            if (task.duration > AnyDoorConfig.MAX_SHOW_LIMIT) {
                task.singleLock = false;
            }
        }
        return task;
    }
}
