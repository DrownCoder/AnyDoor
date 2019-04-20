package com.xuan.android.library.core;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.model.Task;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import static com.xuan.android.library.AnyDoorConfig.*;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务队列
 */
public class TaskEngine extends Handler {
    private static final int TYPE_SHOW = 1; // 显示布局
    private static final int TYPE_DISMISS = 2; // 隐藏布局
    private static final int TYPE_DIRECT_SHOW = 3; // 直接显示布局
    private volatile Queue<Task> taskQueue;//任务队列
    private volatile Queue<Task> runQueue;//执行队列
    private WeakReference<View> injectView;
    private boolean runningLock;

    public TaskEngine() {
        taskQueue = new PriorityBlockingQueue<>(MAX_QUEUE_SIZE);
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
            sendEmptyMessageAtTime(TYPE_SHOW, target.startTime);
        }
    }

    public void dismiss(Task task) {
        if (task == null) {
            return;
        }
        Message message = Message.obtain();
        message.obj = task;
        message.what = TYPE_DISMISS;
        //2.task已经执行，不在队列中,则执行成功后，隐藏
        sendMessageDelayed(message, task.duration);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TYPE_SHOW:
                if (!runningLock) {
                    //如果当前正在执行，这当前这个消息过时了，放弃执行
                    Task task = taskQueue.poll();
                    if (task != null) {
                        //执行显示逻辑
                        runningLock = true;
                        View view = task.viewInjector.injectView(AnyDoor.provider().activity());
                        injectView = new WeakReference<>(view);
                        AnyDoor.provider().injector().inject(view, task.viewInjector);
                        //回收Task
                        AnyDoor.provider().factory().recycle(task);
                    }
                }
                break;
            case TYPE_DIRECT_SHOW:
                //直接显示逻辑
                Task showTask = (Task) msg.obj;
                if (showTask != null) {
                    if (injectView != null) {
                        View view = injectView.get();
                        if (view != null) {
                            AnyDoor.provider().injector().remove(view, showTask.viewInjector);
                        }
                    }
                }
                break;
            case TYPE_DISMISS:
                //移除View
                Task missTask = (Task) msg.obj;
                if (missTask != null) {
                    if (injectView != null) {
                        View view = injectView.get();
                        if (view != null) {
                            AnyDoor.provider().injector().remove(view, missTask.viewInjector);
                        }
                    }
                    runningLock = false;
                }
                //没有任务后，执行未执行任务
                startTask();
                break;
        }
    }
}

