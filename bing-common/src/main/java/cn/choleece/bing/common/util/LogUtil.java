package cn.choleece.bing.common.util;

import org.apache.logging.log4j.Logger;

/**
 * 日志工具
 * @author choleece
 * @date 2018/10/21
 */
public class LogUtil {

    private static final String LOG_PREFIX = "*********";

    /**
     * info级别的日志
     * @param logger
     * @param apiDes
     * @param detail
     */
    public static void info(Logger logger, String apiDes, String detail) {
        String info = LOG_PREFIX + apiDes + LOG_PREFIX + detail;
        logger.info(info);
    }

    /**
     * debug 级别日志
     * @param logger
     * @param apiDes
     * @param detail
     */
    public static void debug(Logger logger, String apiDes, String detail) {
        String debug = LOG_PREFIX + apiDes + LOG_PREFIX + detail;
        logger.debug(debug);
    }

    /**
     * error 级别日志
     * @param logger
     * @param apiDes
     * @param detail
     */
    public static void error(Logger logger, String apiDes, String detail) {
        String error = LOG_PREFIX + apiDes + LOG_PREFIX + detail;
        logger.error(error);
    }

}
