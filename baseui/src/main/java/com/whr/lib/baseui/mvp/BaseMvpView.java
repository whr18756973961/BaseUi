package com.whr.lib.baseui.mvp;

import android.app.Dialog;

/**
 * Created by whr on 2018/6/6.
 */

public interface BaseMvpView {
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

    void showStatusLoadingView(String loadingMessage, boolean isHasMinTime);

    void hideStatusView();

}
