package com.xuan.android.anydoor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.BaseViewInjector;
import com.xuan.android.library.ui.LifeViewInjector;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i("xxxxxxxxxxx", msg.what+"");
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
                /*AnyDoor.showView(new BaseViewInjector() {
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
                //TestDialogFragment.newInstance().show(getSupportFragmentManager(), "");
                AnyDoor.showView(new LifeInjector());
            }
        });

        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.showView(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context) {
                        TextView textView = new TextView(context);
                        textView.setText("延迟2s！");
                        return textView;
                    }

                    @Override
                    public int gravity() {
                        return Gravity.BOTTOM;
                    }

                    @Override
                    public long delay() {
                        return 0;
                    }

                    @Override
                    public long duration() {
                        return 5000;
                    }
                });
            }
        });

        findViewById(R.id.test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.showView(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context) {
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
        public View injectView(Context context) {
            TextView textView = new TextView(context);
            textView.setText("延迟5s！");
            return textView;
        }
    }

}
