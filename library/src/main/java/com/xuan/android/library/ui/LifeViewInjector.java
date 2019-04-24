package com.xuan.android.library.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xuan.android.library.life.LifeObserver;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :the description of this file
 */
public abstract class LifeViewInjector extends BaseViewInjector implements LifeObserver {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("xxxxxxxxx", "onCreate");
    }

    @Override
    public void onStart() {
        Log.i("xxxxxxxxx", "onStart");
    }

    @Override
    public void onResume() {
        Log.i("xxxxxxxxx", "onResume");
    }

    @Override
    public void onPause() {
        Log.i("xxxxxxxxx", "onPause");
    }

    @Override
    public void onStop() {
        Log.i("xxxxxxxxx", "onStop");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}