package com.whr.lib.baseui.widget;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.fragment.BaseFragment;


/**
 * Created by whr on 2017/4/28 0028.
 */

public class StatusView {
	private View mStatusView;
	private ViewStub mViewStubStatusView;

	private TextView mTvStatusMessage;
	private ImageView mIvStatusImage;
	private ImageView mIvStatusLoading;

	private Animation mLoadingAnim;
	private BaseFragment mFragment;

	public StatusView(BaseFragment fragment) {
		mFragment = fragment;
	}

	public void inflate() {
		if (mStatusView != null) return;

		mViewStubStatusView = (ViewStub) mFragment.mRootView.findViewById(R.id.viewstub_status_view);
		mStatusView = mViewStubStatusView.inflate();
		mIvStatusImage = (ImageView) mStatusView.findViewById(R.id.iv_status_view_image);
		mIvStatusLoading = (ImageView) mStatusView.findViewById(R.id.iv_status_view_loading);
		mTvStatusMessage = (TextView) mStatusView.findViewById(R.id.tv_status_view_message);
	}

	/**
	 * @param message
	 */
	public void showEmptyView(String message) {
		inflate();
		if (TextUtils.isEmpty(message))
			message = "在这个星球中还没有数据";
		mTvStatusMessage.setText(message);

		if (mLoadingAnim != null) mLoadingAnim.cancel();
		mIvStatusLoading.clearAnimation();
		mIvStatusLoading.setVisibility(View.GONE);

		mStatusView.setVisibility(View.VISIBLE);

		mFragment.mContentView.setVisibility(View.GONE);
	}

	/**
	 * @param message
	 */
	public void showLoadingView(String message) {
		inflate();
		if (TextUtils.isEmpty(message))
			message = "这个星球正在加载数据";
		mTvStatusMessage.setText(message);

		if (mLoadingAnim == null) {
			mLoadingAnim = AnimationUtils.loadAnimation(mFragment.getContext(), R.anim.loading_round_rotate);
			mLoadingAnim.setInterpolator(new LinearInterpolator());
		}

		mIvStatusLoading.setVisibility(View.VISIBLE);
		mIvStatusLoading.startAnimation(mLoadingAnim);
		mStatusView.setVisibility(View.VISIBLE);

		mFragment.mContentView.setVisibility(View.GONE);
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

		if (mLoadingAnim != null) mLoadingAnim.cancel();
		mIvStatusLoading.clearAnimation();
		mIvStatusLoading.setVisibility(View.GONE);

		mStatusView.setVisibility(View.VISIBLE);

		mFragment.mContentView.setVisibility(View.GONE);
	}

	/**
	 * 隐藏状态页面
	 */
	public void hideStatusView() {
		if (mStatusView == null)
			return;
		if (mLoadingAnim != null)
			mLoadingAnim.cancel();
		mStatusView.setVisibility(View.GONE);
		mFragment.mContentView.setVisibility(View.VISIBLE);
	}
}
