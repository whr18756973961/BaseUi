package com.whr.helloword.baseui;

import android.app.Application;
import android.content.Context;

import com.whr.helloword.baseui.impl.UiCoreProxyImpl;
import com.whr.lib.baseui.helper.UiCoreHelper;

/**
 * Created by 开发 on 2018/4/17.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        UiCoreHelper.setProxy(new UiCoreProxyImpl());
    }

    public static Context getContext() {
        return mContext;
    }
}
