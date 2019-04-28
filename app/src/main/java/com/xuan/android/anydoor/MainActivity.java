package com.xuan.android.anydoor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.BaseViewInjector;
import com.xuan.android.library.ui.DialogViewInject;
import com.xuan.android.library.ui.LifeViewInjector;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("xxxxxxxxxxx", msg.what + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnyDoor.init(getApplication());
        handler.sendEmptyMessageAtTime(123, 0);
        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AnyDoor.openDoor(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context) {
                        TextView textView = new TextView(context);
                        textView.setText("延迟1s！");
                        return textView;
                    }

                    @Override
                    public long delay() {
                        return 1000;
                    }

                    @Override
                    public long duration() {
                        return 6000;
                    }
                });*/
                TestDialogFragment.newInstance().show(getSupportFragmentManager(), "");
                //AnyDoor.openDoor(new LifeInjector());
            }
        });

        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.openDoor(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(R.layout.msg_layout,
                                parent, false);
                    }

                    @Override
                    public int gravity() {
                        return Gravity.CENTER;
                    }
                });
            }
        });

        findViewById(R.id.test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.openDoor(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context, ViewGroup parent) {
                        TextView textView = new TextView(context);
                        textView.setText("延迟5s！");
                        return textView;
                    }

                    @Override
                    public int gravity() {
                        return Gravity.BOTTOM;
                    }

                    @Override
                    public long delay() {
                        return 5000;
                    }

                    @Override
                    public long duration() {
                        return 5000;
                    }
                });
            }
        });
    }

    private static class LifeInjector extends LifeViewInjector {

        @Override
        public View injectView(Context context, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText("延迟5s！");
            return textView;
        }
    }

}
