package com.sanzhi.appupdate.util;

import java.io.File;

/**
 * @author: parade岁月
 * @date: 2020/6/1 11:44
 * @description 辅助工具
 */
public class AuxiliaryUtil {

    /**
     * 检查字符串是否是空白字符
     * @param s 字符串
     * @return true：是 false:不是
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取全路径中的文件名
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }
}
