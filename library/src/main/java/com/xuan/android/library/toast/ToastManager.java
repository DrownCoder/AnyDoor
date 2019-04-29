package com.xuan.android.library.toast;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.xuan.android.library.toast.helper.DefaultToastStyle;
import com.xuan.android.library.toast.helper.IToastStyle;
import com.xuan.android.library.toast.helper.ToastBuilder;
import com.xuan.android.library.toast.helper.ToastHandler;
import com.xuan.android.library.toast.helper.ToastUtil;
import com.xuan.android.library.toast.helper.support.BaseToast;
import com.xuan.android.library.toast.helper.support.SafeToast;
import com.xuan.android.library.toast.helper.support.SupportToast;


/**
 * Author : xuan.
 * Date : 2019/4/13.
 * Description :toast 全局管理类，无需权限，兼容9.0以下
 */
public class ToastManager {
    private static Toast sToast;
    private static ToastHandler sToastHandler;
    private static IToastStyle sDefaultStyle;
    private static Application app;

    public static void init(Application application) {
        app = application;
        // 检查默认样式是否为空，如果是就创建一个默认样式
        if (sDefaultStyle == null) {
            sDefaultStyle = new DefaultToastStyle();
        }
        sToast = initToast(application, sDefaultStyle);
        // 创建一个吐司处理类
        sToastHandler = new ToastHandler(sToast);
    }

    /**
     * 初始化Toast
     */
    private static Toast initToast(Application application, IToastStyle style) {
        //构建Toast
        Toast toast = createToast(application);
        // 初始化布局
        if (style.getToastView() != null) {
            ToastUtil.setView(toast, style.getToastView());
        } else {
            ToastUtil.setView(toast, ToastUtil.createTextView(application.getApplicationContext(),
                    style));
        }
        // 初始化位置
        ToastUtil.setGravity(toast, style.getGravity(), style.getXOffset(),
                style.getYOffset());
        return toast;
    }

    /**
     * 创建Toast
     */
    private static Toast createToast(Application application) {
        // 判断有没有通知栏权限
        if (ToastUtil.isNotificationEnabled(application)) {
            // 解决 Android 7.1 上发现主线程被阻塞后吐司会报错的问题
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                return new SafeToast(application);
            } else {
                return new BaseToast(application);
            }
        } else {
            return new SupportToast(application);
        }
    }


    /**
     * 检查吐司状态,判断是否初始化
     */
    private static boolean checkToastState() {
        // 吐司工具类还没有被初始化，必须要先调用init方法进行初始化
        return sToast == null;
    }


    public static void showToast(CharSequence toast) {
        if (checkToastState()) return;
        if (toast == null || "".equals(toast.toString())) return;

        sToastHandler.add(toast);
        sToastHandler.show();
    }

    public static void showToast(CharSequence str, IToastStyle toastBuilder) {
        if (toastBuilder == null || TextUtils.isEmpty(str)) {
            return;
        }
        if (checkToastState()) return;
        Toast toast = initToast(app, toastBuilder);
        sToastHandler.add(str);
        sToastHandler.setPageToast(toast);
        sToastHandler.show();
    }

    public static void showToast(ToastBuilder builder) {
        showToast(builder.getToast(), builder);
    }

    public static void cancel() {
        sToastHandler.cancel();
    }

}
