package com.xuan.android.library.ui.base;

import com.xuan.android.library.AnyDoorConfig;

/**
 * Author : xuan.
 * Date : 2019/4/26.
 * Description :不自动消失的弹窗
 */
public abstract class DialogViewInjector extends BaseViewInjector {

    @Override
    public long duration() {
        return AnyDoorConfig.UN_AUTO_CANCEL_TOAST;
    }
}
