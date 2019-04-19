package com.xuan.android.library.core;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.model.Task;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.xuan.android.library.AnyDoorConfig.*;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务队列
 */
public class TaskEngine extends Handler {
    private static final int TYPE_SHOW = 1; // 显示布局
    private static final int TYPE_DISMISS = 2; // 显示布局
    private volatile Queue<Task> taskQueue;//任务队列
    private volatile Queue<Task> runQueue;//执行队列
    private volatile Queue<Task> pendingTask;//待执行队列
    private WeakReference<View> injectView;
    private boolean running;

    public TaskEngine() {
        taskQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        pendingTask = new LinkedBlockingQueue<>();
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (taskQueue.isEmpty() || !taskQueue.contains(task)) {
            // 添加一个元素并返回true，如果队列已满，则返回false
            if (!taskQueue.offer(task)) {
                //执行队列满了，则加入到待执行队列
                if (pendingTask.isEmpty() || !pendingTask.contains(task)) {
                    pendingTask.add(task);
                }
            }
            sendEmptyMessageAtTime(TYPE_SHOW, SystemClock.uptimeMillis() + task.delay);
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
                Task task = taskQueue.peek();
                if (task != null) {
                    //执行显示逻辑
                    View view = task.viewInjector.injectView(AnyDoor.provider().application());
                    injectView = new WeakReference<>(view);
                    AnyDoor.provider().injector().inject(view, task.viewInjector);
                    //回收Task
                    AnyDoor.provider().factory().recycle(task);
                } else {

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
                }
                //没有任务后，执行待执行任务
                for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
                    add(pendingTask.peek());
                }
                break;
        }
    }
}

