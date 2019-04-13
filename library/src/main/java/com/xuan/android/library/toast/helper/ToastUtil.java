package com.xuan.android.library.toast.helper;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :Toast工具类
 */
public class ToastUtil {

    /**
     * 检查通知栏权限有没有开启
     * 参考SupportCompat包中的方法： NotificationManagerCompat.from(context).areNotificationsEnabled();
     */
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context
                    .APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer
                        .TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField
                        ("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
            } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException |
                    IllegalAccessException | RuntimeException | ClassNotFoundException ignored) {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 设置吐司的位置
     *
     * @param gravity 重心
     * @param xOffset x轴偏移
     * @param yOffset y轴偏移
     */
    public static void setGravity(Toast toast, int gravity, int xOffset, int yOffset) {
        if (toast == null) {
            return;
        }
        // 适配 Android 4.2 新特性，布局反方向（开发者选项 - 强制使用从右到左的布局方向）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            gravity = Gravity.getAbsoluteGravity(gravity, toast.getView().getResources()
                    .getConfiguration().getLayoutDirection());
        }

        toast.setGravity(gravity, xOffset, yOffset);
    }

    /**
     * 生成默认的 TextView 对象,当前必须用 Application 的上下文创建的 View，否则可能会导致内存泄露
     */
    public static TextView createTextView(Context context, IToastStyle toastStyle) {
        if (toastStyle == null || context == null) {
            return null;
        }
        GradientDrawable drawable = new GradientDrawable();
        // 设置背景色
        drawable.setColor(toastStyle.getBackgroundColor());
        // 设置圆角大小
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                toastStyle.getCornerRadius(), context.getResources().getDisplayMetrics()));

        TextView textView = new TextView(context);
        textView.setId(android.R.id.message);
        textView.setTextColor(toastStyle.getTextColor());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TypedValue.applyDimension(TypedValue
                .COMPLEX_UNIT_SP, toastStyle.getTextSize(), context.getResources()
                .getDisplayMetrics()));

        textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                toastStyle.getPaddingLeft(), context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toastStyle
                        .getPaddingTop(), context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toastStyle
                        .getPaddingRight(), context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toastStyle
                        .getPaddingBottom(), context.getResources().getDisplayMetrics()));

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // setBackground API 版本兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(drawable);
        } else {
            textView.setBackgroundDrawable(drawable);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置 Z 轴阴影
            textView.setZ(toastStyle.getZ());
        }

        if (toastStyle.getMaxLines() > 0) {
            // 设置最大显示行数
            textView.setMaxLines(toastStyle.getMaxLines());
        }

        return textView;
    }


    public static void setView(Toast toast, View view) {
        // 当前必须用 Application 的上下文创建的 View，否则可能会导致内存泄露
        if (toast == null || view == null || view.getContext() != view.getContext()
                .getApplicationContext()) {
            return;
        }
        // 如果吐司已经创建，就重新初始化吐司
        // 取消原有吐司的显示
        toast.cancel();
        toast.setView(view);
    }
}
