package com.xuan.android.library.toast;


import android.view.Gravity;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :全局Toast配置
 */
public class ToastConfig {
    public static final int SHORT_DURATION_TIMEOUT = 2000; // 短吐司显示的时长
    public static final int LONG_DURATION_TIMEOUT = 3500; // 长吐司显示的时长
    // Toast队列最大容量
    public static final int MAX_TOAST_CAPACITY = 3;
    // Toast的圆角
    public static final int TOAST_RADIUS = 6;
    // Toast的背景色
    public static final int TOAST_BACKGROUND = 0XEE575757;
    // Toast的字体大小,单位SP
    public static final int TOAST_TEXT_SIZE = 16;
    // Toast的字体颜色
    public static final int TOAST_TEXT_COLOR = 0XEEFFFFFF;
    // Toast的最大行数限制
    public static final int TOAST_MAX_LINE = 3;
    // Toast的位置
    public static final int TOAST_GRAVITY = Gravity.BOTTOM;


    /**
     * 以下属性不常改动
     */
    // Toast的左Padding,单位DP
    public static final int TOAST_PADDING_LEFT = 16;
    // Toast的右Padding,单位DP
    public static final int TOAST_PADDING_RIGHT = 16;
    // Toast的上Padding,单位DP
    public static final int TOAST_PADDING_TOP = 10;
    // Toast的下Padding,单位DP
    public static final int TOAST_PADDING_BOTTOM = 10;
    // Toast的Z轴深度
    public static final int TOAST_Z = 30;
    // Toast的x方向的偏移量
    public static final int TOAST_X_OFFSET = 0;
    // Toast的y方向的偏移量
    public static final int TOAST_Y_OFFSET = 240;

}
