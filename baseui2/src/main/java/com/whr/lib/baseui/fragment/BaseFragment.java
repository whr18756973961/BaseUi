package com.whr.lib.baseui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.activity.BaseActivity;
import com.whr.lib.baseui.impl.BaseView;
import com.whr.lib.baseui.swipeback.SwipeBackFragment;

/**
 * Created by 开发 on 2018/4/16.
 */

public abstract class BaseFragment extends SwipeBackFragment implements BaseView {
    public BaseActivity mActivity;
    public static final int FFCID = R.id.fragment_base_root;
    /**
     * 根布局->fragment_base
     */
    public View mRootView;
    /**
     * 根布局下面的父控件
     */
    public LinearLayout mRootLayout;

    /**
     * 内容布局->getLayoutId()
     */
    public View mContentView;

    public View mFakeStatusBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity)
            mActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            // 初始化基础布局
            mRootView = inflater.inflate(R.layout.fragment_base, container, false);
            mRootLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_base_root);

            // 虚拟状态栏
            mFakeStatusBar = LayoutInflater.from(getContext()).inflate(R.layout.layout_fake_statusbar, mRootLayout, false);
            mFakeStatusBar.setVisibility(View.GONE);
//            mFakeStatusBar.setBackgroundColor(getContext().getResources().getColor(UiCoreHelper.getProxy().colorPrimaryDark()));
            mRootLayout.addView(mFakeStatusBar, 0);

            // 内容布局
            mContentView = LayoutInflater.from(getContext()).inflate(getLayoutId(), mRootLayout, false);
            mRootLayout.addView(mContentView, 2);
        }

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，
        // 要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) parent.removeView(mRootView);
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleBundle(getArguments());
        initView(view);
    }

    /**
     * 获取布局文件ID
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 参数校验
     *
     * @param bundle
     */
    public void handleBundle(Bundle bundle) {
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * 设置虚拟状态栏的显隐
     *
     * @param visibility
     */
    public void setFakeStatusBarVis(int visibility) {
        mFakeStatusBar.setVisibility(visibility);
    }

    /**
     * header头部的返回键监听时间
     */
    public void onHeaderBackPressed() {
        mActivity.onActivityBackPressed();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        int count = mActivity.getSupportFragmentManager().getBackStackEntryCount();
        if (mActivity.getLayoutId() == 0) {
            enable = count > 1;
        }
        super.setSwipeBackEnable(enable);
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
