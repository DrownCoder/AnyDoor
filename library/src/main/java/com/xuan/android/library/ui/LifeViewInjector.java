package com.xuan.android.library.ui;

import android.content.Intent;
import android.os.Bundle;

import com.xuan.android.library.life.LifeObserver;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :the description of this file
 */
public abstract class LifeViewInjector extends BaseViewInjector implements LifeObserver {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
