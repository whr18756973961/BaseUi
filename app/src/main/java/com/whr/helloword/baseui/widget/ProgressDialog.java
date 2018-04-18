package com.whr.helloword.baseui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.whr.helloword.baseui.R;


/**
 * Created by Administrator on 2017/3/24.
 */

public class ProgressDialog {
    public static Dialog progressBar;
    private static TextView tv_tips;

    public ProgressDialog(Context context) {
        if (progressBar == null) {
            progressBar = new Dialog(context);
            progressBar.setContentView(R.layout.dialog_progressbar);
            tv_tips = progressBar.findViewById(R.id.tv_tips);

            progressBar.setCanceledOnTouchOutside(false);
            progressBar.setCancelable(false);
        }
    }

}
