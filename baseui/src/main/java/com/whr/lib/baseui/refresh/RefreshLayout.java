package com.whr.lib.baseui.refresh;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whr.lib.baseui.R;
import com.whr.lib.baseui.helper.UiCoreHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dafan on 2016/7/2 0002.
 */
public class RefreshLayout extends FrameLayout {

    public static final int NORMAL = 0;
    public static final int LOADING = 1;
    public static final int COMPLETE = 2;
    public static final int END = 3;
    public static final int FAILED = 4;

    @IntDef({NORMAL, LOADING, COMPLETE, END, FAILED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreState {
    }

    private TextView mTvLoadMoreTips;
    private RelativeLayout mRlLoadMoreLayout;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private @LoadMoreState
    int mCurrentLoadStatus;
    private HfRecyclerViewAdapter mHfAdapter;
    private OnRefreshLoadMoreListener mListener;
    private RefStatusView mStatusView;

    public RefreshLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_layout, this, true);
        mCurrentLoadStatus = NORMAL;
    }

    /**
     * 初始化SiwpeRefreshLayout和RecyclerView
     */
    private void initWidget() {
        if (mSwipeRefreshLayout == null) {
            mSwipeRefreshLayout = findViewById(R.id.ref_swipe_refresh_layout);
            int[] colors = UiCoreHelper.getProxy().colorSchemeResources();
            if (colors != null && colors.length != 0) {
                mSwipeRefreshLayout.setColorSchemeResources(colors);
            }
        }

        if (mRecyclerView == null) {
            mRecyclerView = mSwipeRefreshLayout.findViewById(R.id.ref_recyclerivew);
        }
    }

    /**
     * 初始化参数
     *
     * @param loadmore 是否可以上拉加载
     * @param lmanager 布局管理器
     * @param adapter  数据适配器
     */
    public void init(boolean loadmore, RecyclerView.LayoutManager lmanager, RecyclerView.Adapter adapter) {
        initWidget();
        mRecyclerView.setLayoutManager(lmanager);
        mHfAdapter = new HfRecyclerViewAdapter(adapter);

        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener == null)
                    return;
                if (mCurrentLoadStatus == LOADING)
                    return;
                if (!mRecyclerView.isEnabled())
                    return;
                setLoadMoreNormal();
                mListener.onRefresh();
            }
        });

        // 上拉加载更多
        if (loadmore) {
            mRecyclerView.addOnScrollListener(new RecyclerViewScrollListener() {
                @Override
                public void onScrollBottom() {
                    if (mListener == null)
                        return;
                    if (mSwipeRefreshLayout.isRefreshing())
                        return;
                    if (mCurrentLoadStatus == LOADING)
                        return;
                    if (mCurrentLoadStatus == END)
                        return;
                    setLoadMoreLoading();
                    mListener.onLoadMore();
                }
            });

            initLoadMoreLayout();
            mHfAdapter.addFooterView(mHfAdapter.getFooterViewsCount(), mRlLoadMoreLayout);
        }

        // 滚动监听，可用于滚动时候不加载图片等
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                UiCoreHelper.getProxy().onScrollStateChanged(recyclerView, newState);
            }
        });

        mRecyclerView.setAdapter(mHfAdapter);
    }

    /**
     * 获取RecyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        initWidget();
        return mRecyclerView;
    }

    /**
     * 获取SwipeRefreshLayout
     *
     * @return
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        initWidget();
        return mSwipeRefreshLayout;
    }

    /**
     * 初始化加载更多的布局
     */
    private void initLoadMoreLayout() {
        mRlLoadMoreLayout = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_load_more, null);
        mTvLoadMoreTips = mRlLoadMoreLayout.findViewById(R.id.tv_load_more_tips);
        mTvLoadMoreTips.setVisibility(View.GONE);
    }

    /**
     * 首次进入自动下拉刷新，默认延迟300毫秒{@link #autoRefresh(boolean)}
     */
    public void autoRefresh() {
        autoRefresh(true);
    }

    /**
     * 进入自动刷新
     *
     * @param delay 是否延迟300ms
     */
    public void autoRefresh(final boolean delay) {
        initWidget();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mListener == null)
                    return;
                if (!mSwipeRefreshLayout.isEnabled())
                    return;
                if (mCurrentLoadStatus == LOADING)
                    return;
                setLoadMoreNormal();
                mSwipeRefreshLayout.setRefreshing(true);

                if (delay)
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onRefresh();
                        }
                    }, 300);
                else
                    mListener.onRefresh();
            }
        });
    }

    /**
     * 是否在刷新状态
     *
     * @return
     */
    public boolean isRefreshing() {
        initWidget();
        return mSwipeRefreshLayout.isRefreshing();
    }

    /**
     * 设置刷新开关
     *
     * @param enable
     */
    public void setRefreshEnabled(boolean enable) {
        initWidget();
        mSwipeRefreshLayout.setEnabled(enable);
    }

    /**
     * 包含有头部，底部和
     *
     * @return
     */
    public HfRecyclerViewAdapter getHfAdapter() {
        return mHfAdapter;
    }

    /**
     * @return
     */
    public RecyclerView.Adapter getAdapter() {
        return getHfAdapter().getInnerAdapter();
    }

    /**
     * 获取当前加载更多的状态
     *
     * @return
     */
    @LoadMoreState
    public int getCurrentLoadMoreStatus() {
        return mCurrentLoadStatus;
    }

    /**
     * 刷新或者加载完成
     */
    public void setComplete(boolean hasMore) {
        setRefreshComplete();
        setLoadMoreComplete(hasMore);
    }

    /**
     * 刷新或者加载失败
     */
    public void setFailure() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
        else
            setLoadMoreFailed();
    }

    /**
     * 设置刷新完成
     */
    @Deprecated
    public void setRefreshComplete() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置加载更多成功啦
     */
    @Deprecated
    public void setLoadMoreComplete(final boolean hasMore) {
        if (hasMore) {
            mCurrentLoadStatus = COMPLETE;
            if (mTvLoadMoreTips != null)
                mTvLoadMoreTips.setText("上拉加载更多数据");
        } else {
            mCurrentLoadStatus = END;
            if (mTvLoadMoreTips != null)
                mTvLoadMoreTips.setText("数据已经全部加载完成");
        }

        if (mTvLoadMoreTips != null)
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTvLoadMoreTips.setVisibility(hasMore ? View.VISIBLE : View.GONE);
                }
            }, 300);
    }

    /**
     * 设置加载更多失败啦
     */
    public void setLoadMoreFailed() {
        mCurrentLoadStatus = FAILED;
        if (mTvLoadMoreTips != null)
            mTvLoadMoreTips.setText("加载更多失败，上拉可以重新加载");
    }

    /**
     * 设置加载状态恢复正常
     */
    public void setLoadMoreNormal() {
        mCurrentLoadStatus = NORMAL;
    }

    /**
     * 设置正在加载中
     */
    public void setLoadMoreLoading() {
        mCurrentLoadStatus = LOADING;
        if (mTvLoadMoreTips != null)
            mTvLoadMoreTips.setText("正在加载更多···");
    }

    /**
     * 设置监听
     *
     * @param l
     */
    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener l) {
        if (l == null)
            return;
        mListener = l;
    }

    /**
     * 初始化状态页面
     */
    private void initStatusView() {
        if (mStatusView == null)
            mStatusView = new RefStatusView(this);
    }

    /**
     * 显示空数据状态页面
     *
     * @param emptyMessage
     */
    public void showStatusEmptyView(String emptyMessage) {
        initStatusView();
        mStatusView.showEmptyView(emptyMessage);
    }

    /**
     * 显示错误状态页面
     *
     * @param errorMessage
     */
    public void showStatusErrorView(String errorMessage) {
        initStatusView();
        mStatusView.showErrorView(errorMessage);
    }

    /**
     * 显示加载中状态界面
     *
     * @param loadingMessage
     */
    public void showStatusLoadingView(String loadingMessage) {
        initStatusView();
        mStatusView.showLoadingView(loadingMessage);
    }

    /**
     * 隐藏状态界面
     */
    public void hideStatusView() {
        if (mStatusView != null)
            mStatusView.hideStatusView();
    }

    /**
     * 错误状态页面的点击重试回调方法
     */
    public void onStatusErrorViewReplyClick() {
        if (mErrorClickListener != null)
            mErrorClickListener.onStatusErrorViewReplyClick();
    }

    public interface OnStatusErrorClickListener {
        void onStatusErrorViewReplyClick();
    }

    private OnStatusErrorClickListener mErrorClickListener;

    public void setOnStatusErrorClick(OnStatusErrorClickListener l) {
        mErrorClickListener = l;
    }
}
