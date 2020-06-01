package com.sanzhi.appupdate.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/***
 *author: parade岁月
 *date:  2020/5/9 10:10
 *description：对比检查应用版本是否是最新
 */
public class VersionUtil {

    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 检查应用版本是否是线上最新的
     * @param context context
     * @param onLineVersion  线上应用的版本
     * @return true 需要更新  false不需要更新
     */
    public static boolean checkNewVersion(Context context, String onLineVersion){
        String buildVersion = getVersionName(context);
        if (buildVersion.equals(onLineVersion)) return false;//最新,不需要更新
        String[] buildVersionArray = buildVersion.split("\\.");
        String[] onLineVersionArray = onLineVersion.split("\\.");

        int index = 0;
        //获取两个数组的长度的最小值
        int minLen = Math.min(buildVersionArray.length, onLineVersionArray.length);
        int diff = 0;
        //循环判断在minLen内，两个版本的的不同，并记录不同的索引
        while (index < minLen
                && (diff = Integer.parseInt(buildVersionArray[index])
                - Integer.parseInt(onLineVersionArray[index])) == 0) {
            index++;
        }
        //diff有可能是-1,如果buildVersionArray[index] - onLineVersionArray[index]) = -1
        if (diff == 0){
            //在最小长度内是一致的
            //在最小长度内一致，则比较长度大的版本，如果长度大的是线上版本，则需要更新，反之，则不需要
            if (onLineVersionArray.length > minLen){
                //线上的数组长
                boolean flag = false;
                for (int i = minLen; i < onLineVersionArray.length; i++) {
                    if (!"0".equals(onLineVersionArray[i])){
                        flag = true;//如果线上的版本数组长，且多余的部分有不是0，线上的版本较新(需要更新)，需要重置标志位
                        break;
                    }
                }
                if (!flag) {
                    //相同位数内一致且线上数组长且多余的全是0，不需要更新
                    return false;
                }else {
                    //相同位数内一致且线上数组长且不全是0，需要更新
                    return true;//需要更新
                }
            }else {
                //相同位数内一致且本地版本数组长，肯定不需要更新
                return false;
            }

        }else {
            //相同位数内不一致且 本地的版本<线上的版本，需要更新
            return diff < 0;
        }
    }
}
