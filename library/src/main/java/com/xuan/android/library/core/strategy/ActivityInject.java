package com.xuan.android.library.core.strategy;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.core.InjectViewManager;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :只支持Activity的注入，无法覆盖Dialog，PopupWindow,Toast,不支持页面跳转
 * 原理：向DecorView的R.id.content的FrameLayout来addView
 */
public class ActivityInject implements InjectStrategy {

    @Override
    public void inject(View view, final IViewInjector viewInjector) {
        ViewGroup parent = parentView();
        if (parent == null) {
            return;
        }
        //从父布局移除
        InjectViewManager.removeViewFromParent(view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = viewInjector.gravity();
        } else {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT, viewInjector.gravity());
        }
        Log.i(AnyDoorConfig.TAG, view.getContext().getClass().getSimpleName());
        parent.addView(view, params);
        final Animator animator = viewInjector.enter(view);
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    AnyDoor.provider().engine().dismiss(
                            AnyDoor.provider().factory().create(viewInjector));
                }
            });
            animator.start();
        } else {
            AnyDoor.provider().engine().dismiss(
                    AnyDoor.provider().factory().create(viewInjector));
        }
    }

    @Override
    public void remove(final View view, IViewInjector viewInjector) {
        if (viewInjector == null || view == null) {
            return;
        }
        if (view.getParent() != null && InjectViewManager.checkViewAttachStatus(view)) {
            Animator animator = viewInjector.out(view);
            if (animator != null) {
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        InjectViewManager.removeViewFromParent(view);
                    }
                });
                animator.start();
            } else {
                InjectViewManager.removeViewFromParent(view);
            }
        }
    }

    @Override
    public ViewGroup parentView() {
        final Activity activity = AnyDoor.provider().activity();
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        //获取Activity的父布局
        return activity.getWindow().getDecorView().findViewById(android.R.id
                .content);
    }
}
