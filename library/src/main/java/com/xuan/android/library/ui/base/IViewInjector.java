package com.xuan.android.library.ui.base;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :注入View
 */
public interface IViewInjector {
    /**
     * 注入View
     */
    View injectView(Context context, ViewGroup parent);

    /**
     * 进入动画
     */
    Animator enter(View view);

    /**
     * 移除动画
     */
    Animator out(View view);

    /**
     * 持续时间,毫秒
     */
    long duration();

    /**
     * 延时，毫秒
     */
    long delay();

    /**
     * 位置
     */
    int gravity();
}
