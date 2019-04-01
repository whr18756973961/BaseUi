package kotlin.whr.com.kbaseui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.whr.lib.baseui.helper.UiCoreHelper;

/**
 * 通过在UICore中动态设置的waitdialog的资源文件，生成的dialog
 * Created by whr on 2018/4/19.
 */

public class WaitProgressDialog {
    private Dialog progressBar;
    private TextView tv_tips;
    private Context mContext;

    public WaitProgressDialog(Context context) {
        this.mContext = context;
    }

    /**
     * @param
     */
    public void show() {
        show("正在加载", false);
    }

    public void show(String tips) {
        show("正在加载");
    }

    public void show(boolean isCancle) {
        show("正在加载", isCancle);
    }

    public void show(String tips, boolean isCancle) {
        init(tips, isCancle);
        progressBar.show();
    }

    private void init(String tips, boolean isCancle) {
        if (progressBar == null) {
            progressBar = new Dialog(mContext);
            View view = LayoutInflater.from(mContext).inflate(UiCoreHelper.getProxy().waitDialogRes(), null);
            progressBar.setContentView(view);
            tv_tips = view.findViewWithTag("tv_tips");
            if (TextUtils.isEmpty(tips))
                tips = "正在加载";
            tv_tips.setText(tips);
            progressBar.setCancelable(isCancle);
            progressBar.setCanceledOnTouchOutside(false);
        }
    }

    public Dialog getWaitDialog() {
        if (progressBar == null)
            init("正在加载", false);
        return progressBar;
    }

    public boolean isShowing() {
        if (progressBar == null)
            init("正在加载", false);
        return progressBar.isShowing();
    }

    public void dismiss() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }
}
