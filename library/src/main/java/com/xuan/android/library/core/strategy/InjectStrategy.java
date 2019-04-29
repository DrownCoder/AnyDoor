package com.xuan.android.library.core.strategy;

import android.view.View;
import android.view.ViewGroup;

import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :注入策略
 */
public interface InjectStrategy {
    //注入方式
    void inject(View view, IViewInjector viewInjector);

    //移除方式
    void remove(View view, IViewInjector viewInjector);

    ViewGroup parentView();
}
