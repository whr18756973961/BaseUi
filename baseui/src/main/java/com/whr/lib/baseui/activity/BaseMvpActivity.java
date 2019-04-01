package com.whr.lib.baseui.activity;

import android.os.Bundle;

import com.whr.lib.baseui.mvp.BaseMvpPresenter;
import com.whr.lib.baseui.utils.MvpUtils;

/**
 * Created by whr on 2018/6/6.
 */

public abstract class BaseMvpActivity<P extends BaseMvpPresenter> extends BaseActivity {
    public P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initMvp();
        super.onCreate(savedInstanceState);
    }

    private void initMvp() {
        presenter = MvpUtils.getT(this, 0);
        presenter.attchView(this);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
    }


}
