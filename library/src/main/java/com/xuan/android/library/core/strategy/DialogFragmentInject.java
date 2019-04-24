package com.xuan.android.library.core.strategy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :DialogFragment类型的注入
 */
public class DialogFragmentInject implements InjectStrategy {
    @Override
    public void inject(View view, final IViewInjector viewInjector) {
        if (viewInjector == null || view == null) {
            return;
        }
        Fragment fragment = AnyDoor.provider().fragment();
        if (fragment == null || !fragment.isAdded()) {
            return;
        }
        DialogFragment dialogFragment;
        if (fragment instanceof DialogFragment) {
            dialogFragment = (DialogFragment) fragment;
        } else {
            return;
        }
        if (dialogFragment.getDialog().getWindow() == null) {
            return;
        }
        //获取DialogFragment的父布局
        FrameLayout content = dialogFragment.getDialog().getWindow().getDecorView().findViewById
                (android.R.id.content);
        //从父布局移除
        removeViewFromParent(view);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).gravity = viewInjector.gravity();
        } else {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT, viewInjector.gravity());
        }
        content.addView(view, params);
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
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeViewFromParent(view);
                }
            });
        } else {
            removeViewFromParent(view);
        }
    }

    private void removeViewFromParent(View view) {
        //从父布局移除
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) parent;
            vp.removeView(view);
        }
    }
}