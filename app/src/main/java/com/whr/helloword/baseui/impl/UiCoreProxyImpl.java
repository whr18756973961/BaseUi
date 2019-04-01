package com.whr.helloword.baseui.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.whr.helloword.baseui.App;
import com.whr.helloword.baseui.R;
import com.whr.lib.baseui.proxy.IUiCoreProxy;


/**
 * ui 代理类
 * Created by whr
 * on 2017/4/28 0028.
 */

public class UiCoreProxyImpl extends IUiCoreProxy {
    @Override
    public Context getContext() {
        return App.getContext();
    }

    @Override
    public int colorPrimary() {
        return R.color.colorPrimary;
    }

    @Override
    public int colorPrimaryDark() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int[] colorSchemeResources() {
        return new int[0];
    }

    @Override
    public int colorAccent() {
        return R.color.colorAccent;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    @Override
    public int waitDialogRes() {
        return R.layout.dialog_progressbar;
    }

    @Override
    public Class<? extends Fragment> loginFragment() {
        return null;
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
    }
}
