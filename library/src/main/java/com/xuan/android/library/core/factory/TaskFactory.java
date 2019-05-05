package com.xuan.android.library.core.factory;

import android.os.SystemClock;

import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.model.Task;
import com.xuan.android.library.ui.base.IViewInjector;


/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务构建
 */
public class TaskFactory implements ITaskFactory {

    @Override
    public Task create(IViewInjector viewInjector, boolean constrained) {
        Task task = new Task(viewInjector);
        task.delay = viewInjector.delay();
        task.startTime = SystemClock.uptimeMillis() + task.delay;
        task.duration = viewInjector.duration();
        task.asyncLock = constrained;
        if (task.asyncLock) {
            //如果弹窗大于显示时长限制，则进化为特殊弹窗，不受任务队列限制
            if (task.duration > AnyDoorConfig.MAX_SHOW_LIMIT) {
                task.asyncLock = false;
            }
        }
        return task;
    }

    @Override
    public Task create(IViewInjector viewInjector) {
        return create(viewInjector, true);
    }
}
