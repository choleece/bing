package cn.choleece.bing.common.util;

public class CommonUtil {

    public static String objToString(Object obj) {
        if (null == obj) {
            return "";
        }
        return String.valueOf(obj);
    }
}
