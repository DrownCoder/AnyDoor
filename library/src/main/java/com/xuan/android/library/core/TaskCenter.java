package com.xuan.android.library.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.model.Task;

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

    private volatile Task runningTask;//正在执行的任务
    private volatile boolean runningLock;//是否执行任务的标识为

    public TaskCenter(Looper mainLooper) {
        super(mainLooper);
        taskQueue = new PriorityBlockingQueue<>();
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (!task.singleLock) {
            //立即执行,不受同一时间只能显示一个的限制
            Message message = Message.obtain();
            message.obj = task;
            message.what = TYPE_DIRECT_SHOW;
            sendMessageAtTime(message, task.startTime);
        } else {
            if (!taskQueue.contains(task)) {
                //按照显示时间的优先级插入队列
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
     * 取消某个任务
     */
    public void dismiss(Task task) {
        if (task == null) {
            return;
        }
        if (task.duration == AnyDoorConfig.UN_AUTO_CANCEL_TOAST) {
            return;
        }
        Message message = Message.obtain();
        message.obj = task;
        message.what = TYPE_DISMISS;
        //2.task已经执行，不在队列中,则执行成功后，隐藏
        sendMessageDelayed(message, task.duration);
    }

    /**
     * 取消执行的任务
     */
    public void cancelRunningTask() {
        if (runningTask != null) {
            dismiss(runningTask);
        }
    }

    /**
     * 取消所有任务
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
                    runningTask = taskQueue.poll();

                    //否则取出任务执行
                    if (runningTask != null) {
                        //执行显示逻辑
                        Log.i("xxxxxxxxxx", "执行任务！");
                        runningLock = AnyDoor.provider().inject(runningTask.viewInjector, false);
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
                Task missTask = (Task) msg.obj;
                if (missTask != null) {
                    if (AnyDoor.provider().remove(missTask.viewInjector)) {
                        //是否是任务队列移除，如果是，则重置任务队列标志
                        runningLock = false;
                        runningTask = null;
                    }
                }
                //没有任务后，执行未执行任务
                startTask();
                break;
        }
    }
}

