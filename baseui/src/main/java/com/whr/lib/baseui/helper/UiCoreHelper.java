package com.whr.lib.baseui.helper;

import com.whr.lib.baseui.proxy.IUiCoreProxy;

/**
 * Created by dafan on 2017/3/29 0029.
 */

public class UiCoreHelper {
	private static IUiCoreProxy proxy;

	public static void setProxy(IUiCoreProxy iUiCoreProxy) {
		UiCoreHelper.proxy = iUiCoreProxy;
	}

	public static IUiCoreProxy getProxy() {
		if (proxy == null)
			throw new NullPointerException("IUiCoreProxy is null, plase use setUiCoreProxy(setUiCoreProxy iUiCoreProxy) method in somewhere");
		return proxy;
	}
}
