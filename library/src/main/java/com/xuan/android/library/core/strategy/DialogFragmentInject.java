package com.xuan.android.library.core.strategy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.AnyDoorConfig;
import com.xuan.android.library.core.InjectViewManager;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :DialogFragment类型的注入
 * 只支持全屏幕的DialogFragment
 */
public class DialogFragmentInject implements InjectStrategy {
    @Override
    public void inject(View view, final IViewInjector viewInjector) {
        ViewGroup parent = parentView();
        if (parent == null) {
            return;
        }
        //设置消失监听
        DialogFragment fragment = AnyDoor.provider().dialogFragment();
        fragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i(AnyDoorConfig.TAG, "自动取消！");
                AnyDoor.provider().clear();
            }
        });
        //从父布局移除
        InjectViewManager.removeViewFromParent(view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = viewInjector.gravity();
        } else {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT, viewInjector.gravity());
        }
        parent.addView(view, params);
        final Animator animator = viewInjector.enter(view);
        view.post(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    @Override
    public void remove(final View view, IViewInjector viewInjector) {
        if (viewInjector == null || view == null) {
            return;
        }
        Animator animator = viewInjector.out(view);
        if (animator != null && InjectViewManager.checkViewAttachStatus(view)) {
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

    @Override
    public ViewGroup parentView() {
        DialogFragment fragment = AnyDoor.provider().dialogFragment();
        if (fragment == null || !fragment.isAdded()) {
            return null;
        }
        if (fragment.getDialog().getWindow() == null) {
            return null;
        }
        //获取DialogFragment的父布局
        return fragment.getDialog().getWindow().getDecorView().findViewById
                (android.R.id.content);
    }

}
