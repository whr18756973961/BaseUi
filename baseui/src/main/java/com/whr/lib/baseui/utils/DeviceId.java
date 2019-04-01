package com.whr.lib.baseui.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by dafan on 2015/12/24 0024.
 */
public class DeviceId {
    /**
     * 获取设备唯一标识
     * 优先获取Imei生成的ID，因为大部分使用者都是手机，而且准确度较高
     * 如果IMEI获取失败，使用硬件参数生成的ID，这个准确度也比较高，
     * 但是如果用户系统升级、刷机，可能导致ID会变
     * 如果硬件参数生成的还是不行，那就是用android_id
     * android_id可靠性比较差，root手机可能会被修改了
     * 如果android_id还是获取失败了，那么此设备将不能使用本软件
     * 不能使用本软件的概率极其的小，可以忽略不计了
     *
     * @param context
     * @return
     */
    public static final String id(Context context) {
        String id = "";
        String imei = getImeiString(context);
        imei = imei.replace(" ", "");
        if (!TextUtils.isEmpty(imei)) {
            id = imei;
        }

        // 获取AndroidID
        else {
            String android_id = getAndroidId(context);
            android_id = android_id.replace(" ", "");
            if (!TextUtils.isEmpty(android_id))
                id = android_id;
        }

        if (!TextUtils.isEmpty(id))
            return SHA1.sha1(id);
        return "";
    }

    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String getImeiString(Context context) {
        ArrayList<String> imeis = new ImeiUtils(context).getImei();
        if (imeis == null || imeis.isEmpty()) return "";
        else return imeis.toString();
    }
}