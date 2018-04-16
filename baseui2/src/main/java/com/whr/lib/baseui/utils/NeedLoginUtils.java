package com.whr.lib.baseui.utils;

import android.support.v4.app.Fragment;

import com.whr.lib.baseui.helper.UiCoreHelper;
import com.whr.lib.baseui.impl.NeedLogin;


/**
 * Created by whr on 2017/4/27 0027.
 */

public class NeedLoginUtils {
	public static boolean needLogin(Class<? extends Fragment> clazz) {
		if (clazz == null) return false;
		if (clazz.getName().equals(UiCoreHelper.getProxy().loginFragment().getName()))
			return false;
		NeedLogin annotation = clazz.getAnnotation(NeedLogin.class);
		return annotation != null && annotation.value();
	}
}
