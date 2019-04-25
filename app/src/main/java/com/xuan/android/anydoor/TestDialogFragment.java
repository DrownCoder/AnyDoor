package com.xuan.android.anydoor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xuan.android.library.AnyDoor;
import com.xuan.android.library.ui.BaseViewInjector;

/**
 * Author : xuan.
 * Date : 2019/4/24.
 * Description :
 */
public class TestDialogFragment extends BaseFullScreenDialogFragment {

    public static TestDialogFragment newInstance() {
        return new TestDialogFragment();
    }
    @Override
    protected int getLayoutID() {
        return R.layout.test_dialog_fragment;
    }

    @Override
    protected void init() {
        mView.findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyDoor.openDoor(new BaseViewInjector() {
                    @Override
                    public View injectView(Context context) {
                        TextView textView = new TextView(context);
                        textView.setText("延迟1s！");
                        return textView;
                    }

                    @Override
                    public long duration() {
                        return 4000;
                    }
                });
            }
        });
    }
}
