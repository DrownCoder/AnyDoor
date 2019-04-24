package com.xuan.android.anydoor;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author : xuan.
 * Date : 2019/3/26.
 * Description :全屏的DialogFragment
 */
public abstract class BaseFullScreenDialogFragment extends DialogFragment {
    protected Context mContext;
    protected View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        if (!getShowsDialog()) {
            dismiss();
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = inflater.inflate(getLayoutID(), container);
        getDialog().setCanceledOnTouchOutside(true);
        Window win = getDialog().getWindow();
        if (getDialog().getWindow() != null)
            win.setBackgroundDrawable(new ColorDrawable(0x000000));
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
        init();
        return mView;
    }

    protected abstract int getLayoutID();

    protected abstract void init();


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroyView() {
        setShowsDialog(false);
        super.onDestroyView();
    }
}
