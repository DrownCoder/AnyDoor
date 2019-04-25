package com.xuan.android.library.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import static com.xuan.android.library.AnyDoorConfig.DEFAULT_DOOR_DURATION;

/**
 * Author : xuan.
 * Date : 2019/4/15.
 * Description :基础注入View类
 */
public abstract class BaseViewInjector implements IViewInjector {

    @Override
    public Animator enter(View view) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -dp2px(view
                .getContext(), 65), dp2px(view.getContext(), 15));
        translationY.setInterpolator(new OvershootInterpolator());
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1);
        AnimatorSet set = new AnimatorSet();
        set.setTarget(view);
        set.playTogether(translationY, alpha);
        return set;
    }

    @Override
    public Animator out(View view) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", dp2px(view
                .getContext(), 15), -dp2px(view.getContext(), 65));
        translationY.setInterpolator(new AnticipateInterpolator());
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0.1f);
        AnimatorSet set = new AnimatorSet();
        set.setTarget(view);
        set.playTogether(translationY, alpha);
        return set;
    }

    @Override
    public long duration() {
        return DEFAULT_DOOR_DURATION;
    }

    @Override
    public long delay() {
        return 0;
    }

    @Override
    public int gravity() {
        return Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
