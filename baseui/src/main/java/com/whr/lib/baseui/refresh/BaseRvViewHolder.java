package com.whr.lib.baseui.refresh;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dafan on 2017/4/22 0022.
 */

public class BaseRvViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    public BaseRvViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    /**
     * 控件注册
     *
     * @param viewId 控件ID
     * @param <E>
     * @return
     */
    public <E extends View> E findView(@IdRes int viewId) {
        return itemView.findViewById(viewId);
    }

    /**
     * same as {@link RecyclerView.ViewHolder#getAdapterPosition()}
     *
     * @return
     */
    public int position() {
        return getAdapterPosition();
    }
}
