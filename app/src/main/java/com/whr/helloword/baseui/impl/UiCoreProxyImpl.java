package com.whr.helloword.baseui.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.whr.helloword.baseui.App;
import com.whr.helloword.baseui.R;
import com.whr.helloword.baseui.widget.ProgressDialog;
import com.whr.lib.baseui.proxy.IUiCoreProxy;


/**
 * Created by dafan on 2017/4/28 0028.
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
	public int colorAccent() {
		return R.color.colorAccent;
	}

	@Override
	public Dialog waitDialog() {
		return new ProgressDialog();
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
