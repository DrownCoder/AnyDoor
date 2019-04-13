package com.xuan.android.library.toast.helper;

import static com.xuan.android.library.toast.ToastConfig.*;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :默认Toast样式
 */
public class DefaultToastStyle implements IToastStyle {
    @Override
    public int getGravity() {
        return TOAST_GRAVITY;
    }

    @Override
    public int getXOffset() {
        return TOAST_X_OFFSET;
    }

    @Override
    public int getYOffset() {
        return TOAST_Y_OFFSET;
    }

    @Override
    public int getZ() {
        return TOAST_Z;
    }

    @Override
    public int getCornerRadius() {
        return TOAST_RADIUS;
    }

    @Override
    public int getBackgroundColor() {
        return TOAST_BACKGROUND;
    }

    @Override
    public int getTextColor() {
        return TOAST_TEXT_COLOR;
    }

    /**
     * Toast文字大小
     */
    @Override
    public float getTextSize() {
        return TOAST_TEXT_SIZE;
    }

    @Override
    public int getMaxLines() {
        return TOAST_MAX_LINE;
    }

    @Override
    public int getPaddingLeft() {
        return TOAST_PADDING_LEFT;
    }

    @Override
    public int getPaddingTop() {
        return TOAST_PADDING_TOP;
    }

    @Override
    public int getPaddingRight() {
        return TOAST_PADDING_RIGHT;
    }

    @Override
    public int getPaddingBottom() {
        return TOAST_PADDING_BOTTOM;
    }
}
