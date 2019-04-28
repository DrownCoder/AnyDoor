package com.xuan.android.library;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :the description of this file
 */
public class AnyDoorConfig {
    public static final String TAG = "AnyDoor";
    //默认显示时长
    public static final long DEFAULT_DOOR_DURATION = 2000;
    //弹窗的显示时长界限，超过这个时长，则认为是优先弹窗，不受task队列限制
    public static final int MAX_SHOW_LIMIT = 5000;
    //不自动消失的弹窗
    public static final int UN_AUTO_CANCEL_TOAST = -1;
}
