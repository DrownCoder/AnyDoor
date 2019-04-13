package com.xuan.android.library.toast.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static com.xuan.android.library.toast.ToastConfig.*;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2018/11/12
 * desc   : Toast 显示处理类
 */
public final class ToastHandler extends Handler {

    private static final int TYPE_SHOW = 1; // 显示吐司
    private static final int TYPE_CONTINUE = 2; // 继续显示
    private static final int TYPE_CANCEL = 3; // 取消显示

    // 吐司队列
    private volatile Queue<CharSequence> mQueue;

    // 当前是否正在执行显示操作
    private volatile boolean isShow;

    // 吐司对象
    private final Toast mToast;
    // 临时Toast对象,用于特殊页面和全局样式不统一
    private Toast pageToast;

    public ToastHandler(Toast toast) {
        super(Looper.getMainLooper());
        mToast = toast;
        mQueue = new ArrayBlockingQueue<>(MAX_TOAST_CAPACITY);
    }

    public void setPageToast(Toast toast) {
        this.pageToast = toast;
    }

    public void add(CharSequence s) {
        if (mQueue.isEmpty() || !mQueue.contains(s)) {
            // 添加一个元素并返回true，如果队列已满，则返回false
            if (!mQueue.offer(s)) {
                // 移除队列头部元素并添加一个新的元素
                mQueue.poll();
                mQueue.offer(s);
            }
        }
    }

    public void show() {
        if (!isShow) {
            isShow = true;
            sendEmptyMessage(TYPE_SHOW);
        }
    }

    public void cancel() {
        if (isShow) {
            isShow = false;
            sendEmptyMessage(TYPE_CANCEL);
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TYPE_SHOW:
                // 返回队列头部的元素，如果队列为空，则返回null
                CharSequence text = mQueue.peek();
                if (text != null) {
                    getToast().setText(text);
                    getToast().show();
                    clearPageToast();
                    // 等这个 Toast 显示完后再继续显示，要加上一些延迟，不然在某些手机上 Toast 可能会来不及消失
                    sendEmptyMessageDelayed(TYPE_CONTINUE, getToastDuration(text) + 300);
                } else {
                    isShow = false;
                }
                break;
            case TYPE_CONTINUE:
                // 移除并返问队列头部的元素，如果队列为空，则返回null
                mQueue.poll();
                if (!mQueue.isEmpty()) {
                    sendEmptyMessage(TYPE_SHOW);
                } else {
                    isShow = false;
                }
                break;
            case TYPE_CANCEL:
                isShow = false;
                mQueue.clear();
                getToast().cancel();
                clearPageToast();
                break;
            default:
                break;
        }
    }

    /**
     * 清除页面Toast
     */
    private void clearPageToast() {
        pageToast = null;
    }

    /**
     * 当有页面Toast的时候，优先显示页面Toast
     */
    private Toast getToast() {
        if (pageToast != null) {
            return pageToast;
        }
        return mToast;
    }

    /**
     * 根据文本来获取吐司的显示时间
     */
    private static int getToastDuration(CharSequence text) {
        // 如果显示的文字超过了10个就显示长吐司，否则显示短吐司
        return text.length() > 20 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
    }
}