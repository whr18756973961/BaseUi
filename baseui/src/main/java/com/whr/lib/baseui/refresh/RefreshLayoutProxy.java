package com.whr.lib.baseui.refresh;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dafan on 2017/4/5 0005.
 */

public interface RefreshLayoutProxy {
    void onScrollStateChanged(RecyclerView recyclerView, int newState);

    int[] colorSchemeResources();
}
