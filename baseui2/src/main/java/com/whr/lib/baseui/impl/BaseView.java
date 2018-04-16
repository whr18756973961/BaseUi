package com.whr.lib.baseui.impl;

import android.app.Dialog;

/**
 * Created by 开发 on 2018/4/16.
 */

public interface BaseView {
    void showWaitDialog();

    void showWaitDialog(String message);

    void showWaitDialog(String message, boolean cancelable);

    boolean isWaitDialogShow();

    Dialog getWaitDialog();

    void hideWaitDialog();

    void showToast(String msg);

    void showStatusEmptyView(String emptyMessage);

    void showStatusErrorView(String emptyMessage);

    void showStatusLoadingView(String loadingMessage);

    void hideStatusView();
}
