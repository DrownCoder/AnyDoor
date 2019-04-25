package com.xuan.android.library.core.factory;

import com.xuan.android.library.model.Task;
import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :任务加工工厂
 */
public interface ITaskFactory {
    Task create(IViewInjector viewInjector, boolean constrained);

    Task create(IViewInjector viewInjector);
}
