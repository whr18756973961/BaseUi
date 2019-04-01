package com.whr.lib.baseui.mvp;

import com.whr.lib.baseui.helper.UiCoreHelper;

/**
 * Created by whr on 2018/6/6.
 */

public abstract class BaseMvpPresenter<V extends BaseMvpView> {
    public V view;

    public void attchView(V v) {
        view = v;
    }


    public <T> T attchCall(T t) {
        return UiCoreHelper.getProxy().onMvpCall(this, t);
    }

    public void detachView() {
        UiCoreHelper.getProxy().onMvpDestory(this);
        this.view = null;
    }
}
