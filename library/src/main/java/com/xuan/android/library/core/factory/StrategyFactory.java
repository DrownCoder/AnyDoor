package com.xuan.android.library.core.factory;

import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.xuan.android.library.core.strategy.ActivityInject;
import com.xuan.android.library.core.strategy.DialogFragmentInject;
import com.xuan.android.library.core.strategy.InjectStrategy;
import com.xuan.android.library.life.ActivityObserver;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/28.
 * Description :注入策略选择
 */
public class StrategyFactory implements IInjectStrategyFactory {
    private ActivityObserver activityObserver;
    private InjectStrategy injectStrategy;
    private DialogFragmentInject dialogViewInject;

    public StrategyFactory(ActivityObserver activityObserver) {
        this.activityObserver = activityObserver;
        injectStrategy = new ActivityInject();
    }

    @Override
    public InjectStrategy injectStrategy(IViewInjector viewInjector) {
        //如果当前弹出的是一个全屏的DialogFragment,则需要特殊的注入策略，注入到DialogFragment这个Window中
        if (activityObserver.getCurFragment() instanceof DialogFragment && checkDialogValid
                ((DialogFragment) activityObserver.getCurFragment())) {
            if (dialogViewInject == null) {
                dialogViewInject = new DialogFragmentInject();
            }
            return dialogViewInject;
        }
        return injectStrategy;
    }

    /**
     * 校验DialogFragment符合注入要求:是一个全屏幕的DialogFragment
     * 否则不能向DialogFragment注入，因为不是全屏的，那么注入的布局会被缩放
     *
     * @param dialogFragment 当前的DialogFragment
     * @return true-可以注入DialogFragment false-还是注入Activity
     */
    private boolean checkDialogValid(DialogFragment dialogFragment) {
        if (dialogFragment.getDialog().getWindow() == null) {
            return false;
        }
        WindowManager.LayoutParams windowParams = dialogFragment.getDialog().getWindow()
                .getAttributes();
        if (windowParams == null || windowParams.width != ViewGroup.LayoutParams.MATCH_PARENT ||
                windowParams.height != ViewGroup.LayoutParams.MATCH_PARENT) {
            return false;
        }
        return true;
    }
}
