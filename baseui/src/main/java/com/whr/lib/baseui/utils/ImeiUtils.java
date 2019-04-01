package com.whr.lib.baseui.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by dafan on 2015/12/23 0023.
 */
public class ImeiUtils {
    private Context mContext;

    public ImeiUtils(Context context) {
        this.mContext = context;
    }

    public ArrayList<String> getImei() {
        ArrayList<String> imeis = new ArrayList<>();

        // 原生方法获取IMEI
        String imei1 = getPhoneImei();
        if (checkImei(imei1)) {
            imeis.add(imei1);
        }

        // MTK
        ArrayList<String> mtk = mtk();
        for (String imei : mtk)
            if (!imeis.contains(imei)) imeis.add(imei);

        // MTK2
        ArrayList<String> mtk2 = mtk2();
        for (String imei : mtk2)
            if (!imeis.contains(imei)) imeis.add(imei);

        // MTK3
        ArrayList<String> mtk3 = mtk3();
        for (String imei : mtk3)
            if (!imeis.contains(imei)) imeis.add(imei);

        // spread
        ArrayList<String> spread = spread();
        for (String imei : spread)
            if (!imeis.contains(imei)) imeis.add(imei);

        // qualcomm
        ArrayList<String> qualcomm = qualcomm();
        for (String imei : qualcomm)
            if (!imeis.contains(imei)) imeis.add(imei);

        // other
        ArrayList<String> other = other();
        for (String imei : other)
            if (!imeis.contains(imei)) imeis.add(imei);

        ArrayList<String> other2 = other2();
        for (String imei : other2)
            if (!imeis.contains(imei)) imeis.add(imei);

        return imeis;
    }

    /**
     * 通过原生方式获取IEMI
     * {@code <android.permission.READ_PHONE_STATE>}
     *
     * @return
     */
    public String getPhoneImei() {
        if (mContext.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE,
                mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            String imei = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (checkImei(imei))
                return imei;
        }
        return "";
    }

    /**
     * mkt
     *
     * @return
     */
    protected ArrayList<String> mtk() {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Integer simId_1, simId_2;
            try {
                java.lang.reflect.Field fields1 = c.getField("GEMINI_SIM_1");
                fields1.setAccessible(true);
                simId_1 = (Integer) fields1.get(null);
                java.lang.reflect.Field fields2 = c.getField("GEMINI_SIM_2");
                fields2.setAccessible(true);
                simId_2 = (Integer) fields2.get(null);
            } catch (Exception ex) {
                simId_1 = 0;
                simId_2 = 1;
            }

            Method m1 = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);
            String imei_1 = ((String) m1.invoke(tm, simId_1)).trim();
            String imei_2 = ((String) m1.invoke(tm, simId_2)).trim();

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * mtk2
     *
     * @return
     */
    protected ArrayList<String> mtk2() {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");

            Integer simId_1, simId_2;
            try {
                java.lang.reflect.Field fields1 = c.getField("GEMINI_SIM_1");
                fields1.setAccessible(true);
                simId_1 = (Integer) fields1.get(null);
                java.lang.reflect.Field fields2 = c.getField("GEMINI_SIM_2");
                fields2.setAccessible(true);
                simId_2 = (Integer) fields2.get(null);
            } catch (Exception e) {
                simId_1 = 0;
                simId_2 = 1;
            }

            Method mx = TelephonyManager.class.getMethod("getDefault", int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

            String imei_1 = (tm1.getDeviceId()).trim();
            String imei_2 = (tm2.getDeviceId()).trim();

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * mtk3
     *
     * @return
     */
    protected ArrayList<String> mtk3() {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");

            Integer simId_1, simId_2;
            try {
                java.lang.reflect.Field fields1 = c.getField("GEMINI_SIM_1");
                fields1.setAccessible(true);
                simId_1 = (Integer) fields1.get(null);
                java.lang.reflect.Field fields2 = c.getField("GEMINI_SIM_2");
                fields2.setAccessible(true);
                simId_2 = (Integer) fields2.get(null);
            } catch (Exception ex) {
                simId_1 = 0;
                simId_2 = 1;
            }

            Method mx = TelephonyManager.class.getMethod("getDeviceId", int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

            String imei_1 = (tm1.getDeviceId()).trim();
            String imei_2 = (tm2.getDeviceId()).trim();

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * 展讯
     *
     * @return
     */
    protected ArrayList<String> spread() {
        try {
            Class<?> c = Class.forName("com.android.internal.telephony.PhoneFactory");
            Method m = c.getMethod("getServiceName", String.class, int.class);
            String spreadTmService = (String) m.invoke(c, Context.TELEPHONY_SERVICE, 1);

            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei_1 = (tm.getDeviceId()).trim();
            TelephonyManager tm1 = (TelephonyManager) mContext.getSystemService(spreadTmService);
            String imei_2 = (tm1.getDeviceId()).trim();

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (ExceptionInInitializerError e) {
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        } catch (Exception e) {
        }
        return new ArrayList<String>();
    }

    /**
     * 高通
     *
     * @return
     */
    protected ArrayList<String> qualcomm() {
        try {
            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
            Object obj = mContext.getSystemService("phone_msim");
            Integer simId_1 = 0;
            Integer simId_2 = 1;
            Method md = cx.getMethod("getDeviceId", int.class);

            String imei_1 = ((String) md.invoke(obj, simId_1)).trim();
            String imei_2 = ((String) md.invoke(obj, simId_2)).trim();

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * 其他
     *
     * @return
     */
    protected ArrayList<String> other() {
        try {
            String imei_1 = getOperatorBySlot(mContext, "getDeviceIdGemini", 0);
            String imei_2 = getOperatorBySlot(mContext, "getDeviceIdGemini", 1);

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * 其他2
     *
     * @return
     */
    protected ArrayList<String> other2() {
        try {
            String imei_1 = getOperatorBySlot(mContext, "getDeviceId", 0);
            String imei_2 = getOperatorBySlot(mContext, "getDeviceId", 1);

            ArrayList<String> imeis = new ArrayList<String>();
            if (checkImei(imei_1)) {
                imeis.add(imei_1);
            }
            if (checkImei(imei_2)) {
                imeis.add(imei_2);
            }
            return imeis;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     * @param context
     * @param predictedMethodName
     * @param slotID
     * @return
     */
    private String getOperatorBySlot(Context context, String predictedMethodName, int slotID) {
        String inumeric = null;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);
            if (ob_phone != null) {
                inumeric = ob_phone.toString();
            }
        } catch (Exception e) {
        }
        return inumeric;
    }

    /**
     * 检查IMEI是否有效
     *
     * @param imei
     * @return
     */
    private boolean checkImei(String imei) {
        if (TextUtils.isEmpty(imei))
            return false;
        if (imei.equals("00000000000000"))
            return false;
        if (imei.equals("000000000000000"))
            return false;
        imei = imei.replace(" ", "");
        int len = imei.length();
        return len == 14 || len == 15;
    }
}
