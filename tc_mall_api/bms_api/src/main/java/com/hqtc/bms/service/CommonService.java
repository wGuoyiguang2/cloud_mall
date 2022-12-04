package com.hqtc.bms.service;

/**
 * Created by wanghaoyang on 18-8-10.
 */
public interface CommonService {

    /**
     * 判断一个字符串是否是逗号分割正数数字的格式,如"1231,123123,123"
     * add by wangahoyang at 20180801
     * @param str 要判断的字符串
     * @return true是|false否
     * */
    boolean isPositiveNumericByComma(String str);

    /**
     * 判断一个字符串是否是逗号分割数字的格式,如"-1231,123123,-123"
     * add by wanghaoyang at 20180810
     * @param str 要判断的字符串
     * @return true是|false否
     * */
    boolean isNumericByComma(String str);

    /**
     * 将时间戳格式化为字符串形式
     * add by wanghaoyang at 20180920
     * @param mtime 当前时间戳,精确到ms
     * */
    String timeFormatToString(long mtime);
}
