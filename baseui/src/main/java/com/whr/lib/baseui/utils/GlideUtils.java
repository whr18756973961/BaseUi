package com.whr.lib.baseui.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.whr.lib.baseui.helper.UiCoreHelper;

import java.io.File;
import java.math.BigDecimal;


/**
 * Created by dafan on 2017/3/30 0030.
 */

public class GlideUtils {
    /**
     * @param cxt
     * @param iv
     * @param path
     */
    public static void load(Context cxt, ImageView iv, Object path) {
//        load(cxt, iv, path, UiCoreHelper.getProxy().glidePlaceholderRes());
    }

    public static void load(ImageView iv, Object path) {
//        load(iv.getContext(), iv, path, UiCoreHelper.getProxy().glidePlaceholderRes());
    }

    public static void load(Context cxt, ImageView iv, Object path, boolean noErr) {
        if (noErr)
            load(cxt, iv, path, 0);
//        else load(cxt, iv, path, UiCoreHelper.getProxy().glidePlaceholderRes());
    }

    /**
     * @param cxt
     * @param iv
     * @param path
     * @param btf
     */
    public static void load(Context cxt, ImageView iv, Object path, Transformation... btf) {
        load(cxt, iv, path, 0, 0, btf);
    }

    /**
     * @param cxt
     * @param iv
     * @param path
     * @param placeId
     */
    public static void load(Context cxt, ImageView iv, Object path, @DrawableRes int placeId) {
//        load(cxt, iv, path, placeId, UiCoreHelper.getProxy().glidePlaceholderRes());
    }

    /**
     * @param cxt
     * @param iv
     * @param path
     * @param placeId
     * @param errorId
    //     */
//    public static void load(Context cxt, ImageView iv, Object path, @DrawableRes int placeId, @DrawableRes int errorId) {
//        load(cxt, iv, path, placeId, errorId, new CropCircleTransformation[0]);
//    }

    /**
     * @param cxt
     * @param iv
     * @param path
     * @param placeId
     * @param errorId
     */
    public static void load(Context cxt, ImageView iv, Object path, @DrawableRes int placeId, @DrawableRes int errorId, Transformation... btf) {
        if (iv == null) return;
        if (cxt == null) return;
        if (path == null) return;

        RequestManager manager = Glide.with(cxt);
        DrawableRequestBuilder<?> builder = optimize(manager, path);
        if (placeId != 0)
            builder.placeholder(placeId);
        if (errorId != 0)
            builder.error(errorId);
        if (btf != null && btf.length > 0)
            builder.bitmapTransform(btf);
        builder.diskCacheStrategy(DiskCacheStrategy.ALL);
        builder.dontAnimate();
        builder.into(iv);
    }

    /**
     * @param manager
     * @param path
     * @return
     */
    private static DrawableRequestBuilder optimize(RequestManager manager, Object path) {
        if (path instanceof byte[]) {
            return manager.load((byte[]) path);
        }
        if (path instanceof File) {
            return manager.load((File) path);
        }
        if (path instanceof Integer) {
            return manager.load((Integer) path);
        }
        if (path instanceof String) {
            return manager.load((String) path);
        }
        if (path instanceof Uri) {
            return manager.load((Uri) path);
        }
        return manager.load(path);
    }

    /**
     * @param cxt
     * @param iv
     * @param path
     */
    public static void loadgif(Context cxt, ImageView iv, Object path) {
        if (iv == null) return;
        if (cxt == null) return;
        if (path == null) return;
        RequestManager manager = Glide.with(cxt);
        GifTypeRequest<?> builder = optimize2(manager, path);
        builder.diskCacheStrategy(DiskCacheStrategy.ALL);
        builder.dontAnimate();
        builder.into(iv);
    }

    /**
     * @param manager
     * @param path
     * @return
     */
    private static GifTypeRequest<?> optimize2(RequestManager manager, Object path) {
        if (path instanceof byte[]) {
            return manager.load((byte[]) path).asGif();
        }
        if (path instanceof File) {
            return manager.load((File) path).asGif();
        }
        if (path instanceof Integer) {
            return manager.load((Integer) path).asGif();
        }
        if (path instanceof String) {
            return manager.load((String) path).asGif();
        }
        if (path instanceof Uri) {
            return manager.load((Uri) path).asGif();
        }
        return manager.load(path).asGif();
    }

    public static void resumeRequestsRecursive(Context context) {
        Glide.with(context).resumeRequestsRecursive();
    }

    public static void pauseRequestsRecursive(Context context) {
        Glide.with(context).pauseRequestsRecursive();
    }


    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        if (file == null) return size;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }


    /**
     * 根据原型url地址和指定圆角弧度参数获取完整圆角图片链接
     *
     * @param url   原型url地址
     * @param round 圆角弧度 dp
     * @return 完整圆角图片链接
     */
    public static String optImgRound(String url, int round) {
        String optImg = "?x-oss-process=image/resize,w_200/rounded-corners,r_" + round + "/format,png";
        return url + optImg;
    }

    public static String optImg(String url) {
        String optImg = "?x-oss-process=image/resize,w_200/rounded-corners,r_10/format,png";
        return url + optImg;
    }

    public static String optImg(String url, int w) {
        String optImg = "?x-oss-process=image/resize,w_" + w + "/rounded-corners,r_10/format,png";
        return url + optImg;
    }

    public static String optImg(String url, int w, int r) {
        String optImg = "?x-oss-process=image/resize,w_" + w + "/rounded-corners,r_" + r + "/format,png";
        return url + optImg;
    }

    public static String optImg_r(String url, int r) {
        String optImg = "?x-oss-process=image/rounded-corners,r_" + r + "/format,png";
        return url + optImg;
    }
}
