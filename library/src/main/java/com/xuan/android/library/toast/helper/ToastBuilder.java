package com.xuan.android.library.toast.helper;

import android.support.annotation.ColorInt;
import android.view.View;

import com.xuan.android.library.toast.ToastConfig;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :Toast样式构造器
 */
public class ToastBuilder implements IToastStyle {
    private CharSequence toast;
    private View toastView;
    private int gravity = ToastConfig.TOAST_GRAVITY;
    private int xOffset = ToastConfig.TOAST_X_OFFSET;
    private int yOffset = ToastConfig.TOAST_Y_OFFSET;
    private int z = ToastConfig.TOAST_Z;
    private int cornerRadius = ToastConfig.TOAST_RADIUS;
    private int backgroundColor = ToastConfig.TOAST_BACKGROUND;
    private int textColor = ToastConfig.TOAST_TEXT_COLOR;
    private int textSize = ToastConfig.TOAST_TEXT_SIZE;
    private int maxLine = ToastConfig.TOAST_MAX_LINE;
    private int paddingLeft = ToastConfig.TOAST_PADDING_LEFT;
    private int paddingTop = ToastConfig.TOAST_PADDING_TOP;
    private int paddingRight = ToastConfig.TOAST_PADDING_RIGHT;
    private int paddingBottom = ToastConfig.TOAST_PADDING_BOTTOM;

    public static ToastBuilder init() {
        return new ToastBuilder();
    }

    public ToastBuilder toast(CharSequence toast) {
        this.toast = toast;
        return this;
    }

    /**
     * Toast展示的样式，Toast不能触发点击事件
     * 不设置的话，默认是一个TextView
     *
     * @param toastView 注意！！！View必须使用Application创建，如果使用Activity创建会产生内存泄漏
     */
    public ToastBuilder view(View toastView) {
        this.toastView = toastView;
        return this;
    }

    public ToastBuilder gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public ToastBuilder xOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public ToastBuilder yOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public ToastBuilder z(int z) {
        this.z = z;
        return this;
    }

    public ToastBuilder radius(int radius) {
        this.cornerRadius = radius;
        return this;
    }

    public ToastBuilder background(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public ToastBuilder textColor(@ColorInt int textColor) {
        this.textColor = textColor;
        return this;
    }

    public ToastBuilder textSize(@ColorInt int textSize) {
        this.textSize = textSize;
        return this;
    }

    public ToastBuilder maxLine(int maxLine) {
        this.maxLine = maxLine;
        return this;
    }

    public ToastBuilder paddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public ToastBuilder paddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public ToastBuilder paddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public ToastBuilder paddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    @Override
    public int getGravity() {
        return gravity;
    }

    @Override
    public int getXOffset() {
        return xOffset;
    }

    @Override
    public int getYOffset() {
        return yOffset;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public int getCornerRadius() {
        return cornerRadius;
    }

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }

    @Override
    public float getTextSize() {
        return textSize;
    }

    @Override
    public int getMaxLines() {
        return maxLine;
    }

    @Override
    public int getPaddingLeft() {
        return paddingLeft;
    }

    @Override
    public int getPaddingTop() {
        return paddingTop;
    }

    @Override
    public int getPaddingRight() {
        return paddingRight;
    }

    @Override
    public int getPaddingBottom() {
        return paddingBottom;
    }

    public CharSequence getToast() {
        return toast;
    }

    @Override
    public View getToastView() {
        return toastView;
    }
}
