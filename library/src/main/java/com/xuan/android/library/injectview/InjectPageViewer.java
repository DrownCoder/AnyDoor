package com.xuan.android.library.injectview;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.life.LifeFragment;
import com.xuan.android.library.life.LifeObserver;
import com.xuan.android.library.ui.base.IViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/12.
 * Description :无侵入式注入View给页面
 */
public class InjectPageViewer {

    private volatile static InjectPageViewer instance;

    public InjectPageViewer(Application application) {
        if (application == null) {
            return;
        }
    }

    public static InjectPageViewer init(Application application) {
        if (instance == null) {
            synchronized (InjectPageViewer.class) {
                if (instance == null) {
                    instance = new InjectPageViewer(application);
                }
            }
        }
        return instance;
    }

    public static void show(IViewInjector viewInjector, boolean async) {
        if (viewInjector == null) {
            return;
        }
        Activity target = AnyDoor.provider().activity();
        if (viewInjector instanceof LifeObserver) {
            FragmentManager fragmentManager = target.getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            LifeFragment lifeFragment = LifeFragment.newInstance();
            lifeFragment.setLifeCycle((LifeObserver) viewInjector);
            transaction.add(lifeFragment, "");
            transaction.commitAllowingStateLoss();
        }
        AnyDoor.provider().engine().add(AnyDoor.provider().factory().create(viewInjector,
                async));
    }

    /**
     * 手动触发隐藏任务，若弹窗还在duration期间，也能隐藏
     */
    public static void dismiss(IViewInjector viewInjector, boolean async) {
        if (async) {
            AnyDoor.provider().engine().dismiss(AnyDoor.provider().factory().create(viewInjector),
                    0, false);
        }else{
            AnyDoor.provider().engine().cancel(AnyDoor.provider().factory().create(viewInjector));
        }
    }
}