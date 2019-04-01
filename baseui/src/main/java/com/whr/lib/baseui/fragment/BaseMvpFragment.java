package com.whr.lib.baseui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whr.lib.baseui.mvp.BaseMvpPresenter;
import com.whr.lib.baseui.utils.MvpUtils;

/**
 * Created by whr on 2018/6/6.
 */

public abstract class BaseMvpFragment<P extends BaseMvpPresenter> extends BaseFragment {

    public P presenter;

    private void initMvp() {
        presenter = MvpUtils.getT(this, 0);
        presenter.attchView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMvp();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        super.onDestroyView();
    }

}
