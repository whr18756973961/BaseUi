package com.whr.lib.baseui.refresh;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.whr.lib.baseui.R;

/**
 * Created by dafan on 2017/4/28 0028.
 */

public class RefStatusView {
    private View mStatusView;
    private ViewStub mViewStubStatusView;

    private TextView mTvStatusMessage;
    private ImageView mIvStatusImage;
    private ImageView mIvStatusLoading;

    private Animation mLoadingAnim;
    private AnimationDrawable mLoadingAnim2;
    private RefreshLayout mRefreshLayout;

    public RefStatusView(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

    public void inflate() {
        if (mStatusView != null) return;
        mViewStubStatusView = mRefreshLayout.findViewById(R.id.viewstub_ref_status_view);
        mStatusView = mViewStubStatusView.inflate();
        mIvStatusImage = mStatusView.findViewById(R.id.iv_ref_status_view_image);
        mIvStatusLoading = mStatusView.findViewById(R.id.iv_ref_status_view_loading);
        mTvStatusMessage = mStatusView.findViewById(R.id.tv_ref_status_view_message);
    }

    /**
     * @param message
     */
    public void showEmptyView(String message) {
        inflate();
        if (TextUtils.isEmpty(message))
            message = "在这个星球中还没有数据";
        mTvStatusMessage.setText(message);
        mIvStatusImage.setVisibility(View.VISIBLE);

        if (mLoadingAnim != null) mLoadingAnim.cancel();
        if (mLoadingAnim2 != null && mLoadingAnim2.isRunning()) mLoadingAnim2.stop();

        mIvStatusLoading.clearAnimation();
        mIvStatusLoading.setVisibility(View.GONE);

        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * @param message
     */
    @Deprecated
    public void showLoadingView(String message) {
        if (true) {
            showLoadingView();
            return;
        }

        inflate();
        if (TextUtils.isEmpty(message))
            message = "这个星球正在加载数据";
        mTvStatusMessage.setText(message);
        mIvStatusImage.setVisibility(View.VISIBLE);

        if (mLoadingAnim == null) {
            mLoadingAnim = AnimationUtils.loadAnimation(mRefreshLayout.getContext(), R.anim.loading_round_rotate);
            mLoadingAnim.setInterpolator(new LinearInterpolator());
        }

        mIvStatusLoading.setVisibility(View.VISIBLE);
        mIvStatusLoading.startAnimation(mLoadingAnim);
        mStatusView.setVisibility(View.VISIBLE);
    }

    public void showLoadingView() {
        inflate();
        mIvStatusImage.setVisibility(View.GONE);
        mTvStatusMessage.setVisibility(View.GONE);
        mIvStatusLoading.setVisibility(View.VISIBLE);
        mStatusView.setVisibility(View.VISIBLE);

        if (mLoadingAnim2 == null) {
            mLoadingAnim2 = (AnimationDrawable) mIvStatusLoading.getDrawable();
        }
        if (!mLoadingAnim2.isRunning()) {
            mLoadingAnim2.start();
        }
    }

    /**
     * 显示错误页面
     *
     * @param message
     */
    public void showErrorView(String message) {
        inflate();
        if (TextUtils.isEmpty(message))
            message = "在这个星球中的数据貌似有点问题~~";
        mTvStatusMessage.setText(message);
        mTvStatusMessage.setVisibility(View.VISIBLE);
        mIvStatusImage.setVisibility(View.VISIBLE);

        if (mLoadingAnim != null) mLoadingAnim.cancel();
        if (mLoadingAnim2 != null && mLoadingAnim2.isRunning()) mLoadingAnim2.stop();

        mIvStatusLoading.clearAnimation();
        mIvStatusLoading.setVisibility(View.GONE);

        mStatusView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏状态页面
     */
    public void hideStatusView() {
        if (mStatusView == null)
            return;
        if (mLoadingAnim != null) mLoadingAnim.cancel();
        if (mLoadingAnim2 != null && mLoadingAnim2.isRunning()) mLoadingAnim2.stop();
        mStatusView.setVisibility(View.GONE);
    }
}
