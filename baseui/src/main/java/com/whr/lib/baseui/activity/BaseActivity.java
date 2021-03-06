package com.whr.lib.baseui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.helper.UiCoreHelper;
import com.whr.lib.baseui.mvp.BaseMvpView;
import com.whr.lib.baseui.swipeback.SwipeBackActivity;
import com.whr.lib.baseui.utils.FragmentUtils;
import com.whr.lib.baseui.utils.StatusBarUtil;
import com.whr.lib.baseui.widget.WaitProgressDialog;

/**
 * Created by whr on 2018/4/16.
 */

public abstract class BaseActivity extends SwipeBackActivity implements BaseMvpView, View.OnClickListener {

    private BaseActivity mActivity;
    /**
     * 根布局中可以添加Fragment的container id
     */
    public static int FCID = R.id.fl_container;

    private FrameLayout mFlRootLayout;

    /**
     * 控件声明的集合
     */
    private SparseArray<View> mViews;

    /**
     * 对话框
     */
    private WaitProgressDialog mWaitDialog;
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

    public <V extends View> V findView(@IdRes int viewId, boolean click) {
        if (mViews == null) mViews = new SparseArray<>();
        V view = (V) mViews.get(viewId);
        if (view != null) return view;
        view = (V) findViewById(viewId);
        mViews.put(viewId, view);
        if (click) view.setOnClickListener(this);
        return view;
    }

    /**
     * @param view
     * @param <E>
     */
    public <E extends View> void setOnClick(E view) {
        if (view == null) return;
        view.setOnClickListener(this);
    }

    /**
     * 设置状态栏颜色
     * 重写可以改变状态栏颜色
     */
    public void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(UiCoreHelper.getProxy().colorPrimaryDark()), 0);
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
    public boolean swipeBackPriority() {
        if (getLayoutId() == 0) {
            return super.swipeBackPriority();
        } else {
            return getSupportFragmentManager().getBackStackEntryCount() <= 0;
        }
    }

    @Override
    public void showWaitDialog() {
        showWaitDialog("精彩值得等待");
    }

    @Override
    public void showWaitDialog(String message) {
        if (TextUtils.isEmpty(message))
            showWaitDialog();
        else
            showWaitDialog(message, true);
    }

    @Override
    public void showWaitDialog(String message, boolean cancelable) {
        if (mWaitDialog == null) {
            mWaitDialog = new WaitProgressDialog(this);
        }
        mWaitDialog.show(message, cancelable);
    }

    @Override
    public boolean isWaitDialogShow() {
        return mWaitDialog.isShowing();
    }

    @Override
    public Dialog getWaitDialog() {
        return mWaitDialog.getWaitDialog();
    }

    @Override
    public void hideWaitDialog() {
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(UiCoreHelper.getProxy().getContext(), msg, Toast.LENGTH_SHORT).show();
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
    public void showStatusLoadingView(String loadingMessage, boolean isHasMinTime) {

    }
    @Override
    public void hideStatusView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        UiCoreHelper.getProxy().onActivityStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UiCoreHelper.getProxy().onActivityRestart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UiCoreHelper.getProxy().onActivityResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UiCoreHelper.getProxy().onActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UiCoreHelper.getProxy().onActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UiCoreHelper.getProxy().onActivityDestory(this);
    }

    /**
     * 重写此方法是为了Fragment的onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UiCoreHelper.getProxy().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

    }
}
