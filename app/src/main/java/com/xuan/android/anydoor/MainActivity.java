package com.xuan.android.anydoor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.BaseViewInjector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnyDoor.init(getApplication());
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.showView(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context) {
                        return new ProgressBar(context);
                    }
                });
            }
        });
    }
}
