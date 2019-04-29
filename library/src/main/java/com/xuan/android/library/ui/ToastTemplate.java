package com.xuan.android.library.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuan.android.library.toast.helper.DefaultToastStyle;
import com.xuan.android.library.toast.helper.ToastUtil;
import com.xuan.android.library.ui.base.BaseViewInjector;


/**
 * Author : xuan.
 * Date : 2019/4/29.
 * Description :可以定制时长的Toast模版，Toast类型的View
 */
public class ToastTemplate extends BaseViewInjector {
    private int duration;
    private int gravity;
    private CharSequence toast;

    public ToastTemplate(CharSequence toast, int duration, int gravity) {
        this.toast = toast;
        this.duration = duration;
        this.gravity = gravity;
    }

    @Override
    public View injectView(Context context, ViewGroup parent) {
        TextView textView = ToastUtil.createTextView(context, new DefaultToastStyle());
        textView.setText(toast);
        return textView;
    }

    @Override
    public long duration() {
        return duration;
    }

    @Override
    public int gravity() {
        return gravity;
    }
}