package com.xuan.android.library.life;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Author : xuan.
 * Date : 2018/8/23.
 * Description :监听什么周期的空Fragment
 */

public class LifeFragment extends Fragment {
    private LifeObserver lifeCycle;

    public static LifeFragment newInstance() {
        LifeFragment fragment = new LifeFragment();
        return fragment;
    }

    public void setLifeCycle(LifeObserver lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (lifeCycle != null) {
            lifeCycle.onCreate(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (lifeCycle != null) {
            lifeCycle.onResume();
        }
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (lifeCycle != null) {
            lifeCycle.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        if (lifeCycle != null) {
            lifeCycle.onPause();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (lifeCycle != null) {
            lifeCycle.onStop();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        if (lifeCycle != null) {
            lifeCycle.onStart();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (lifeCycle != null) {
            lifeCycle.onDestroy();
        }
        super.onDestroy();
    }
}
