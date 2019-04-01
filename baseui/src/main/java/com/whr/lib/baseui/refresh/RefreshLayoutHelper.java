package com.whr.lib.baseui.refresh;

/**
 * Created by dafan on 2017/4/5 0005.
 */

public class RefreshLayoutHelper {
    private static RefreshLayoutProxy proxy;

    public static void setProxy(RefreshLayoutProxy proxy) {
        RefreshLayoutHelper.proxy = proxy;
    }

    public static RefreshLayoutProxy getProxy() {
        return proxy;
    }
}