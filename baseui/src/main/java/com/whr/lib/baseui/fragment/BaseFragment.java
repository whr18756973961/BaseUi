package com.whr.lib.baseui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.activity.BaseActivity;
import com.whr.lib.baseui.helper.UiCoreHelper;
import com.whr.lib.baseui.mvp.BaseMvpView;
import com.whr.lib.baseui.swipeback.SwipeBackFragment;
import com.whr.lib.baseui.utils.StatusBarUtils;
import com.whr.lib.baseui.widget.StatusView;

/**
 * Created by 开发 on 2018/4/16.
 */

public abstract class BaseFragment extends SwipeBackFragment implements BaseMvpView, View.OnClickListener {
    public BaseActivity mActivity;
    public static final int FFCID = R.id.fragment_base_root;
    /**
     *
     */
    public View mHeaderView;
    /**
     * 顶部返回键ImageView
     */
    public ImageView mIvHeaderBack;
    /**
     * 顶部左边的ImageView
     */
    public ImageView mIvHeaderLeft;
    /**
     * 顶部中间的ImageView
     */
    public ImageView mIvHeaderCenter;
    /**
     * 顶部右边的ImageView
     */
    public ImageView mIvHeaderRight;
    /**
     * 顶部中间的标题TextView
     */
    public TextView mTvHeaderTitle;
    /**
     * 顶部左边的TextView
     */
    public TextView mTvHeaderLeft;
    /**
     * 顶部右边的TextView
     */
    public TextView mTvHeaderRight;
    /**
     * 顶部下面的Line
     */
    public View mViewHeaderLine;

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

    public StatusView mStatusView;

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
            mRootLayout.addView(mFakeStatusBar, 0);
            ViewGroup.LayoutParams layoutParams = mFakeStatusBar.getLayoutParams();
            layoutParams.height = StatusBarUtils.getStatusBarHeight(getActivity());
            mFakeStatusBar.setLayoutParams(layoutParams);
            mFakeStatusBar.setBackgroundColor(getContext().getResources().getColor(UiCoreHelper.getProxy().colorPrimaryDark()));
            // 顶部Header
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, mRootLayout, false);
            mHeaderView.setBackgroundColor(getResources().getColor(UiCoreHelper.getProxy().colorPrimary()));
            mRootLayout.addView(mHeaderView, 1);
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


    /**
     * 初始化顶部布局控件
     */
    private void initHeaderView() {
        mHeaderView.setBackgroundResource(UiCoreHelper.getProxy().colorPrimary());

        mTvHeaderLeft = findView(R.id.tv_header_left, true);
        mTvHeaderTitle = findView(R.id.tv_header_title, true);
        mTvHeaderRight = findView(R.id.tv_header_right, true);

//        mTvHeaderLeft.setTextColor(getResources().getColor(UiCoreHelper.getProxy().headerTextColor()));
//        mTvHeaderTitle.setTextColor(getResources().getColor(UiCoreHelper.getProxy().headerTextColor()));
//        mTvHeaderRight.setTextColor(getResources().getColor(UiCoreHelper.getProxy().headerTextColor()));

        mIvHeaderLeft = findView(R.id.iv_header_left, true);
        mIvHeaderCenter = findView(R.id.iv_header_center, true);
        mIvHeaderRight = findView(R.id.iv_header_right, true);

//        mViewHeaderLine = findView(R.id.view_header_line);
//        UiCoreHelper.getProxy().onInitHeaderLine(this, mViewHeaderLine);

        mIvHeaderBack = findView(R.id.iv_header_back, false);
//        if (UiCoreHelper.getProxy().headerBackRes() != 0)
//            mIvHeaderBack.setImageResource(UiCoreHelper.getProxy().headerBackRes());
        mIvHeaderBack.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleBundle(getArguments());
        initView(view);
    }

    /**
     * 控件注册，默认不注册点击注册<br/>
     * 如果需要注册点击时间，请调用{@link BaseFragment#setOnClick(View)}方法
     *
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findView(@IdRes int viewId) {
        return findView(viewId, false);
    }

    /**
     * 控件注册
     *
     * @param viewId 控件ID
     * @param click  是否注册点击事件，如果注册，会调用{@link BaseFragment#}方法
     * @param <E>
     * @return
     */
    public <E extends View> E findView(@IdRes int viewId, boolean click) {
        E view = getView().findViewById(viewId);
        if (click) setOnClick(view);
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

    /**
     * 显示对话框
     */
    public void showWaitDialog() {
        mActivity.showWaitDialog();
    }

    @Override
    public void showWaitDialog(String message) {
        mActivity.showWaitDialog(message);
    }

    @Override
    public void showWaitDialog(String message, boolean cancelable) {
        mActivity.showWaitDialog(message, cancelable);
    }

    @Override
    public boolean isWaitDialogShow() {
        return mActivity.isWaitDialogShow();
    }

    @Override
    public Dialog getWaitDialog() {
        return mActivity.getWaitDialog();
    }

    public void hideWaitDialog() {
        mActivity.hideWaitDialog();
    }

    @Override
    public void showToast(String msg) {
        mActivity.showToast(msg);
    }

    private void initStatusView() {
        if (mStatusView == null)
            mStatusView = new StatusView(this);
    }

    @Override
    public void showStatusEmptyView(String emptyMessage) {
        initStatusView();
        mStatusView.showEmptyView(emptyMessage);
    }

    @Override
    public void showStatusErrorView(String emptyMessage) {
        initStatusView();
        mStatusView.showErrorView(emptyMessage);
    }

    @Override
    public void showStatusLoadingView(String loadingMessage) {
        initStatusView();
        mStatusView.showLoadingView(loadingMessage);
    }

    @Override
    public void hideStatusView() {
        if (mStatusView != null)
            mStatusView.hideStatusView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UiCoreHelper.getProxy().onFragmentActivityCreated(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiCoreHelper.getProxy().onFragmentCreate(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        UiCoreHelper.getProxy().onFragmentStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        UiCoreHelper.getProxy().onFragmentStop(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UiCoreHelper.getProxy().onFragmentResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        UiCoreHelper.getProxy().onFragmentPause(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UiCoreHelper.getProxy().onFragmentDestroyView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UiCoreHelper.getProxy().onFragmentDestroy(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        UiCoreHelper.getProxy().onFragmentDetach(this);
    }

    @Override
    public void onClick(View v) {

    }
}
