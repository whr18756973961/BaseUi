package kotlin.whr.com.kbaseui.base

import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout
import com.hazz.kotlinmvp.base.IBaseView
import com.trello.rxlifecycle2.components.RxActivity
import kotlin.whr.com.kbaseui.R

/**
 * Created by whr on 2018/6/6.
 */
abstract class BaseActivity : RxActivity(), IBaseView {
    var mActivity: BaseActivity? = null
    /**
     * 根布局中可以添加Fragment的container id
     */
    var FCID = R.id.fl_container

    var mRLRootLayout: FrameLayout? = null

    var mViews: SparseArray<View>? = null

    var
}