package com.xuan.android.library.toast.helper;

import android.view.WindowManager;

import com.xuan.android.library.AnyDoor;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :获取WindowManager 对象，通过WindowHelper作为中间件隔离外层感知WindowManger过程
 */
final class WindowHelper {

    /**
     * 获取一个WindowManager对象
     */
    WindowManager getWindowManager() {
        return AnyDoor.provider().windowManager();
    }

}