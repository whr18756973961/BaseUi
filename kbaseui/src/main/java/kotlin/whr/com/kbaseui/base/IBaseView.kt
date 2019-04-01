package com.hazz.kotlinmvp.base

import android.app.Dialog

/**
 * @author Jake.Ho
 * created: 2017/10/25
 * desc:
 */
interface IBaseView {
    fun showWaitDialog()

    fun showWaitDialog(message: String)

    fun showWaitDialog(message: String, cancelable: Boolean)

    fun isWaitDialogShow(): Boolean

    fun getWaitDialog(): Dialog

    fun hideWaitDialog()

    fun showToast(msg: String)

    fun showStatusEmptyView(emptyMessage: String)

    fun showStatusErrorView(emptyMessage: String)

    fun showStatusLoadingView(loadingMessage: String)

    fun showStatusLoadingView(loadingMessage: String, isHasMinTime: Boolean)

    fun showNoNetWorkView(msg: String);

    fun hideStatusView()
}
