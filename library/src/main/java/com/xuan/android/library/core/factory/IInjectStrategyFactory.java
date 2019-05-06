package com.xuan.android.library.core.factory;

import com.xuan.android.library.core.strategy.InjectStrategy;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/28.
 * Description :注入的策略工厂，目前支持两种注入策略
 */
public interface IInjectStrategyFactory {
    InjectStrategy injectStrategy(IViewInjector viewInjector);
}
