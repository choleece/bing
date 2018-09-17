package cn.choleece.bing.common.util;

import com.relops.snowflake.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用雪花生成分布式id
 * Created by choleece on 2018/9/16.
 */
public class SnowFlakeUtil {

    private static final Logger logger = LoggerFactory.getLogger(SnowFlakeUtil.class);

    public static long getId() {
        return SingletonSnowFlake.getInstance().next();
    }

    private static class SingletonSnowFlake {

        /**
         * 雪花算法机器号名称，默认从app.property的snowflake.machine.no里取，默认值为1，各项目可根据自己的需要去配置
         */
        private static final String SNOWFLAKE_MACHINE_NO = "snowflake.machine.no";

        private static Snowflake snowflake;

        public static Snowflake getInstance() {
            synchronized (snowflake) {
                if (snowflake == null) {
                    int machineNo = PropertiesFileUtil.getInstance().getInt(SNOWFLAKE_MACHINE_NO);
                    logger.info("snowflake machine no " + machineNo);
                    return new Snowflake(machineNo);
                }
                return snowflake;
            }
        }
    }
}
