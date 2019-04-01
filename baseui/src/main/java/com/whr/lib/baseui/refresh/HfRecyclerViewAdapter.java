package com.whr.lib.baseui.refresh;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * RecyclerView.Adapter with Header and Footer
 */
public class HfRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 10000;

    /**
     * RecyclerView使用的，真正的Adapter
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    public HfRecyclerViewAdapter() {
    }

    public HfRecyclerViewAdapter(RecyclerView.Adapter innerAdapter) {
        setAdapter(innerAdapter);
    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {

        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new IllegalArgumentException("your adapter must be a RecyclerView.Adapter");
        }

        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewsCount(), mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeaderViewsCount(), mInnerAdapter.getItemCount());
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    /**
     * 添加头部
     *
     * @param header
     */
    public void addHeaderView(View header) {
        if (header == null) {
            throw new IllegalArgumentException("header is null");
        }
        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    /**
     * @param footer
     */
    public void addFooterView(View footer) {
        addFooterView(0, footer);
    }

    /**
     * 添加底部
     *
     * @param footer
     */
    public void addFooterView(int position, View footer) {
        if (footer == null) {
            throw new IllegalArgumentException("footer is null");
        }
        if (position > getFooterViewsCount()) {
            throw new IllegalArgumentException("position is " + position + ", but headerview count is " + getHeaderViewsCount());
        }
        mFooterViews.add(position, footer);
        this.notifyDataSetChanged();
    }

    /**
     * 返回第一个HeaderView
     *
     * @return
     */
    public View getHeaderView(int position) {
        if (position >= getFooterViewsCount()) {
            throw new IllegalArgumentException("position is " + position + ", but headerview count is " + getHeaderViewsCount());
        }
        return mHeaderViews.get(position);
    }

    /**
     * 返回第一个FoView
     *
     * @return
     */
    public View getFooterView(int position) {
        if (position >= getFooterViewsCount()) {
            throw new IllegalArgumentException("position is " + position + ", but headerview count is " + getFooterViewsCount());
        }
        return mFooterViews.get(position);
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public boolean hasHeader() {
        return getHeaderViewsCount() != 0;
    }

    public boolean hasFooter() {
        return getFooterViewsCount() != 0;
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + (mInnerAdapter == null ? 0 : mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        int innerCount = mInnerAdapter.getItemCount();
        int headerCount = getHeaderViewsCount();

        // header
        if (position < headerCount) {
            return TYPE_HEADER_VIEW + position;
        }

        // inner
        if (headerCount <= position && position < headerCount + innerCount) {
            int innerItemViewType = mInnerAdapter.getItemViewType(position - headerCount);
            if (innerItemViewType >= Integer.MAX_VALUE / 2) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
            }
            return innerItemViewType + Integer.MAX_VALUE / 2;
        }

        // footer
        return TYPE_FOOTER_VIEW + position - headerCount - innerCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerCount = getHeaderViewsCount();

        // header
        if (viewType < TYPE_HEADER_VIEW + headerCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        }

        // footer
        if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        }

        // inner
        return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerCount = getHeaderViewsCount();

        if (position >= headerCount && position < headerCount + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - headerCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager)) return;
        final int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = getItemViewType(position);
                // header
                if (viewType < TYPE_HEADER_VIEW + getHeaderViewsCount()) {
                    return spanCount;
                }
                // footer
                if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
                    return spanCount;
                }
                return 1;
            }
        });
    }
}