package cn.choleece.code.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 读取资源文件里的值
 * @author choleece
 * @date 2018/9/16
 */
public class PropertiesFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesFileUtil.class);

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();

    /**
     * 默认资源文件当名称
     */
    private static final String DEFAULT_CONFIG_NAME = "app";

    /**
     * 打开文件的时间
     */
    private Date loadTime = null;

    /**
     * 超时时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle;

    private PropertiesFileUtil(String fileName) {
        this.loadTime = new Date();
        resourceBundle = ResourceBundle.getBundle(fileName);
    }

    public static PropertiesFileUtil getInstance() {
        logger.info("get default app.property");
        return getInstance(DEFAULT_CONFIG_NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String fileName) {
        logger.info("get " + fileName + ".property");
        PropertiesFileUtil property = configMap.get(fileName);
        if (property == null || ((System.currentTimeMillis() - property.getLoadTime().getTime()) > TIME_OUT)) {
            property = new PropertiesFileUtil(fileName);
            configMap.put(fileName, property);
        }
        return property;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public Integer getInt(String key) {
        try {
            return Integer.parseInt(resourceBundle.getString(key));
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
