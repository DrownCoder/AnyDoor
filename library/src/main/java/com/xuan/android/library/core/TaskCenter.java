package com.xuan.android.library.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.model.Task;
import com.xuan.android.library.ui.base.IViewInjector;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务中心,主线程的消息队列
 */
public class TaskCenter extends Handler {
    private static final int TYPE_SHOW = 1; // 显示布局
    private static final int TYPE_DISMISS = 2; // 隐藏布局
    private static final int TYPE_DIRECT_SHOW = 3; // 直接显示布局
    private Queue<Task> taskQueue;//任务队列

    private volatile WeakReference<Task> runningTask;//正在执行的任务
    private volatile boolean runningLock;//是否执行任务的标识为

    public TaskCenter(Looper mainLooper) {
        super(mainLooper);
        //优先级队列
        taskQueue = new PriorityBlockingQueue<>();
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (!task.asyncLock) {
            //同步执行，如果delay==0,则直接同步执行，不走消息队列异步
            if (task.delay == 0) {
                Log.i(AnyDoorConfig.TAG, "同步注入");
                AnyDoor.provider().inject(task.viewInjector, true);
            } else {
                Message message = Message.obtain();
                message.obj = task;
                message.what = TYPE_DIRECT_SHOW;
                sendMessageAtTime(message, task.startTime);
            }
        } else {
            if (!taskQueue.contains(task)) {
                //按照显示时间的优先级插入队列
                runningLock = AnyDoor.provider().viewManager().checkViewAttachStatus();
                if (taskQueue.offer(task) && !runningLock) {
                    //正在执行，则不执行任务，等任务执行完成后自动执行任务
                    startTask();
                }
            }
        }
    }

    private void startTask() {
        Task target = taskQueue.peek();
        if (target != null) {
            //发送消息执行
            Log.i("xxxxxxxxxx", "发送消息，执行任务！");
            sendEmptyMessageAtTime(TYPE_SHOW, target.startTime);
        }
    }

    /**
     * 自动隐藏某个View的任务
     * 如果duration == AnyDoorConfig.UN_AUTO_CANCEL_TOAST
     * 则需要使用dismiss(Task task, boolean auto)方法
     */
    public void dismiss(Task task) {
        dismiss(task, true);
    }

    /**
     * duration后自动隐藏
     *
     * @param task 隐藏任务
     * @param auto 是否是自动隐藏,如果为false，说明主动触发的隐藏，可以隐藏标记为UN_AUTO_CANCEL_TOAST的Toast
     */
    public void dismiss(Task task, boolean auto) {
        dismiss(task, task.duration, auto);
    }

    /**
     * duration后自动隐藏，可以调整duration，用于提前隐藏弹窗
     *
     * @param task     隐藏任务
     * @param duration 任务显示时长
     * @param auto     是否是自动隐藏
     */
    public void dismiss(Task task, long duration, boolean auto) {
        if (task == null) {
            return;
        }
        if (auto && duration == AnyDoorConfig.UN_AUTO_CANCEL_TOAST) {
            return;
        }
        Log.i(AnyDoorConfig.TAG, "发送移除消息");
        //移除之前的自动隐藏的任务
        removeMessages(TYPE_DISMISS, task.viewInjector);
        //再次执行隐藏任务
        Message message = Message.obtain();
        message.obj = task.viewInjector;
        message.what = TYPE_DISMISS;
        //2.task已经执行，不在队列中,则执行成功后，隐藏
        sendMessageDelayed(message, duration);
    }

    /**
     * 同步取消一个展示的任务
     */
    public void cancel(Task task) {
        if (task == null) {
            return;
        }
        removeTask(task.viewInjector, task);
    }

    /**
     * 取消执行的任务，不会执行动画，移除展示的消息和隐藏的消息
     */
    public void cancelRunningTask() {
        if (runningTask != null) {
            Task task = runningTask.get();
            if (task != null) {
                Log.i(AnyDoorConfig.TAG, "执行清理");
                removeMessages(TYPE_DISMISS, task.viewInjector);
                removeMessages(TYPE_SHOW, task);
                removeMessages(TYPE_DIRECT_SHOW, task);
            }
        }
        runningLock = false;
    }

    /**
     * 清除所有任务，包括未执行的和待执行的
     */
    public void cancelAllTask() {
        cancelRunningTask();
        removeCallbacksAndMessages(null);
        taskQueue.clear();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TYPE_SHOW:
                //如果当前正在执行，这当前这个消息过时了，放弃执行，继续存入队列
                if (!runningLock) {
                    Task task = taskQueue.poll();

                    //否则取出任务执行
                    if (task != null) {
                        runningTask = new WeakReference<>(task);
                        //执行显示逻辑
                        Log.i("xxxxxxxxxx", "执行任务！");
                        runningLock = AnyDoor.provider().inject(task.viewInjector, false);
                    }
                }
                break;
            case TYPE_DIRECT_SHOW:
                //直接显示逻辑
                Task showTask = (Task) msg.obj;
                if (showTask != null) {
                    AnyDoor.provider().inject(showTask.viewInjector, true);
                }
                break;
            case TYPE_DISMISS:
                //移除View
                IViewInjector viewInjector = (IViewInjector) msg.obj;
                removeTask(viewInjector, runningTask.get());
                break;
        }
    }

    private void removeTask(IViewInjector viewInjector, Task task) {
        if (viewInjector != null) {
            if (AnyDoor.provider().remove(viewInjector)) {
                //是否是任务队列移除，如果是，则重置任务队列标志
                if (runningTask != null) {
                    if (task == runningTask.get()) {
                        runningTask.clear();
                        runningTask = null;
                        runningLock = false;
                    }
                }
            }
        }
        //没有任务后，执行未执行任务
        startTask();
    }
}


