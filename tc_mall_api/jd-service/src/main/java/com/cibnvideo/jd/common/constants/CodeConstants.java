package com.cibnvideo.jd.common.constants;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/30 15:55
 */
public class CodeConstants {
    private CodeConstants() {
    }

    private final static String TOKEN_GET_FORBIDDEN = "2019";
    private final static String TOKEN_INVALID = "1003";
    private final static String TOKEN_OVERDUE = "1004";
    private final static String TOKEN_ABSENCE = "1022";

    public static String getTokenGetForbidden() {
        return TOKEN_GET_FORBIDDEN;
    }

    public static String getTokenInvalid() {
        return TOKEN_INVALID;
    }

    public static String getTokenOverdue() {
        return TOKEN_OVERDUE;
    }

    public static String getTokenAbsence() {
        return TOKEN_ABSENCE;
    }
}
