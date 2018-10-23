package cn.choleece.bing.common.xss;

import cn.choleece.bing.common.exceptions.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * sql预处理
 * @author choleece
 * @date 2018/9/17
 */
public class SqlFilter {

    /**
     * sql 注入过滤
     * @param str
     * @return
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        /**
         * 转小写
         */
        str = str.toLowerCase();

        String[] keyWords = {"select", "update", "truncate", "insert", "delete", "master", "declare", "alter", "drop"};

        for (String word : keyWords) {
            if (str.indexOf(word) != -1) {
                throw new RRException("包含非法字符");
            }
        }
        return str;
    }
}
