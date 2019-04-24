package com.xuan.android.library.life;

import android.content.Intent;
import android.os.Bundle;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :生命周期感知
 */
public interface LifeObserver {
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
