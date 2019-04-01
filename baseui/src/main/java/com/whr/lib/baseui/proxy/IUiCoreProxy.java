package com.whr.lib.baseui.proxy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * Created by whr on 2018/4/16.
 * BaseUi代理类
 */

public abstract class IUiCoreProxy {
    private boolean login = false;

    /**
     * 全局context对象
     *
     * @return
     */
    public abstract Context getContext();

    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    public abstract int colorPrimary();

    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    public abstract int colorPrimaryDark();

    /**
     * 配置下拉刷新空间主题色
     * @return
     */
    public abstract int[] colorSchemeResources();
    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    public abstract int colorAccent();
    /**
     * 配置下拉滚动状态
     * @param recyclerView
     * @param newState
     */
    public abstract void onScrollStateChanged(RecyclerView recyclerView, int newState);
    /**
     * 全局配置等待对话框的布局文件
     *
     * @return
     */
    public abstract int waitDialogRes();

    /**
     * 配置登陆的fragment
     *
     * @return
     */
    public abstract Class<? extends Fragment> loginFragment();

    /**
     * 设置是否登录成功了
     *
     * @param isLogin
     */
    public void login(boolean isLogin) {
        login = isLogin;
    }

    /**
     * 获取登录状态
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * Activity创建时被调用
     *
     * @param activity
     */
    public void onActivityCreate(Activity activity) {

    }

    /**
     * Activity创建或者从后台重新回到前台时被调用
     *
     * @param activity
     */
    public void onActivityStart(Activity activity) {
    }

    /**
     * Activity从后台重新回到前台时被调用
     *
     * @param activity
     */
    public void onActivityRestart(Activity activity) {
    }

    /**
     * Activity创建或者从被覆盖、后台重新回到前台时被调用
     *
     * @param activity
     */
    public void onActivityResume(Activity activity) {
    }

    /**
     * Activity被覆盖到下面或者锁屏时被调用
     *
     * @param activity
     */
    public void onActivityPause(Activity activity) {
    }

    /**
     * 退出当前Activity或者跳转到新Activity时被调用
     *
     * @param activity
     */
    public void onActivityStop(Activity activity) {
    }


    /**
     * 退出当前Activity时被调用,调用之后Activity就结束了
     *
     * @param activity
     */
    public void onActivityDestory(Activity activity) {
    }

    /**
     * Activity onActivityResult
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    }

    /**
     * 当一个Fragment对象关联到一个Activity时调用
     *
     * @param fragment
     */
    public void onFragmentAttach(Fragment fragment) {
    }

    /**
     * 这个方法是在fragment初始化的时候调用，我们通常在这个方法中使用getArgument获取activity传来的初始化fragment的参数
     *
     * @param fragment
     */
    public void onFragmentCreate(Fragment fragment) {
    }

    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param fragment
     */
    public void onFragmentCreateView(Fragment fragment) {
    }

    /**
     * 当Activity对象完成自己的onCreate方法时调用
     *
     * @param fragment
     */
    public void onFragmentActivityCreated(Fragment fragment) {
    }

    /**
     * Fragment对象在ui可见时调用
     *
     * @param fragment
     */
    public void onFragmentStart(Fragment fragment) {
    }

    /**
     * Fragment对象的ui可以与用户交互时调用
     *
     * @param fragment
     */
    public void onFragmentResume(Fragment fragment) {
    }

    /**
     * Fragment对象可见，但不可交互。有Activity对象转为onPause状态时调用
     *
     * @param fragment
     */
    public void onFragmentPause(Fragment fragment) {
    }

    /**
     * 有空间完全遮挡；或者宿主Activity对象转为onStop状态时调用
     *
     * @param fragment
     */
    public void onFragmentStop(Fragment fragment) {
    }

    /**
     * Fragment对象清理view资源时调用，也就是移除fragment中的视图
     *
     * @param fragment
     */
    public void onFragmentDestroyView(Fragment fragment) {
    }

    /**
     * Fragment对象完成对象清理View资源时调用
     *
     * @param fragment
     */
    public void onFragmentDestroy(Fragment fragment) {
    }

    /**
     * Fragment对象没有与Activity对象关联时调用
     *
     * @param fragment
     */
    public void onFragmentDetach(Fragment fragment) {
    }

    /**
     * 在MVP中使用网络的调用，比如：retrofit/okhttp/rxjava等
     * 主要用于收集网络请求，在页面结束的时候取消这些网络请求
     *
     * @param object
     * @param t
     */
    public <T> T onMvpCall(Object object, T t) {
        return t;
    }

    /**
     * 在页面退出的时候调用，会取消所有的网络请求
     *
     * @param object
     */
    public void onMvpDestory(Object object) {
    }

}
