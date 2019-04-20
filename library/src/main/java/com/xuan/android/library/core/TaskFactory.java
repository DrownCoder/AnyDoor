package com.xuan.android.library.core;

import com.xuan.android.library.core.factory.ITaskFactory;
import com.xuan.android.library.model.Task;
import com.xuan.android.library.ui.IViewInjector;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务构建
 */
public class TaskFactory implements ITaskFactory {
    private Queue<Task> taskPool;

    public TaskFactory() {
        taskPool = new ArrayBlockingQueue<>(10);
    }

    @Override
    public Task create(IViewInjector viewInjector) {
        Task task = taskPool.peek();
        if (task == null) {
            task = new Task(viewInjector);
        } else {
            task.recover(viewInjector);
        }
        return task;
    }

    @Override
    public void recycle(Task task) {
        task.recycle();
        taskPool.offer(task);
    }
}
