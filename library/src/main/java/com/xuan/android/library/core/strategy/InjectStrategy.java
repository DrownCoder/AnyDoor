package com.xuan.android.library.core.strategy;

import android.view.View;

import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :注入策略
 */
public interface InjectStrategy {
    void inject(IViewInjector viewInjector);

    void remove(View view, IViewInjector viewInjector);
}
