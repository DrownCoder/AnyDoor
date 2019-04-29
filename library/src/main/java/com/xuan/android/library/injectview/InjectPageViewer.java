package com.xuan.android.library.injectview;

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

    public static void show(IViewInjector viewInjector, boolean constrained) {
        if (viewInjector == null) {
            return;
        }
        if (viewInjector instanceof LifeObserver) {
            FragmentManager fragmentManager = AnyDoor.provider().activity().getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            LifeFragment lifeFragment = LifeFragment.newInstance();
            lifeFragment.setLifeCycle((LifeObserver) viewInjector);
            transaction.add(lifeFragment, "");
            transaction.commitAllowingStateLoss();
        }
        AnyDoor.provider().engine().add(AnyDoor.provider().factory().create(viewInjector,
                constrained));
    }

    public static void dismiss(IViewInjector viewInjector) {
        AnyDoor.provider().engine().dismiss(AnyDoor.provider().factory().create(viewInjector));
    }
}
