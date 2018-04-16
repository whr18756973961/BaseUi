package com.whr.lib.baseui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.impl.BaseView;
import com.whr.lib.baseui.swipeback.SwipeBackActivity;
import com.whr.lib.baseui.utils.FragmentUtils;

/**
 * Created by whr on 2018/4/16.
 */

public abstract class BaseActivity extends SwipeBackActivity implements BaseView {

    private BaseActivity mActivity;
    /**
     * 根布局中可以添加Fragment的container id
     */
    public static int FCID = R.id.fl_container;

    private FrameLayout mFlRootLayout;

    /**
     * 对话框
     */
    private ProgressDialog mWaitDialog;
    /**
     * 是否支持双击，默认为不支持
     */
    private boolean mDoubleClickEnable = false;
    /**
     * 上一次点击的时间戳
     */
    private long mLastClickTime;
    /**
     * 被判断为重复点击的时间间隔
     */
    private final long MIN_CLICK_DELAY_TIME = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mFlRootLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        mFlRootLayout.setFitsSystemWindows(isFitsSystemWindows());
        if (getLayoutId() != 0)
            mFlRootLayout.addView(getLayoutInflater().inflate(getLayoutId(), null), -1, -1);
        setContentView(mFlRootLayout);
        if (null != getIntent()) handleIntent(getIntent());
        initView(mFlRootLayout);
    }


    /**
     * 设置应用布局时是否考虑系统窗口布局<br/>
     * 默认是true
     *
     * @return
     */
    public boolean isFitsSystemWindows() {
        return true;
    }


    /**
     * return R.layout.activity_base
     *
     * @return
     */
    public FrameLayout getRootView() {
        return mFlRootLayout;
    }

    /**
     * 获取Intent
     *
     * @param intent
     */
    public void handleIntent(@NonNull Intent intent) {
    }

    /**
     * 返回页面布局
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 初始化控件
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (isDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 检测双击
     */
    public boolean isDoubleClick() {
        if (mDoubleClickEnable) return false;
        long time = System.currentTimeMillis();
        if (time - mLastClickTime > MIN_CLICK_DELAY_TIME) {
            mLastClickTime = time;
            return false;
        } else {
            return true;
        }
    }

    /**
     * 处理返回操作
     *
     * @return
     */
    public boolean onActivityBackPressed() {
        // 如果getLayoutId()==0说明没有加载Activity自己的布局，只是单独加载了Fragment
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (getLayoutId() == 0 && count == 1) {
            onActivityFinish();
            return true;
        }

        if (getLayoutId() != 0 && count == 0) {
            onActivityFinish();
            return true;
        }
        FragmentUtils.popFragment(getSupportFragmentManager());
        return false;
    }
    /**
     * 当activity结束时候调用
     */
    public void onActivityFinish() {
        finish();
    }
    @Override
    public void showWaitDialog() {

    }

    @Override
    public void showWaitDialog(String message) {

    }

    @Override
    public void showWaitDialog(String message, boolean cancelable) {

    }

    @Override
    public boolean isWaitDialogShow() {
        return false;
    }

    @Override
    public Dialog getWaitDialog() {
        return null;
    }

    @Override
    public void hideWaitDialog() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showStatusEmptyView(String emptyMessage) {

    }

    @Override
    public void showStatusErrorView(String emptyMessage) {

    }

    @Override
    public void showStatusLoadingView(String loadingMessage) {

    }

    @Override
    public void hideStatusView() {

    }
}
