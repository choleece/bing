package cn.choleece.bing.common.cache;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存工具
 * @author sf
 */
@Component
public class J2CacheUtil {
    @Autowired
    private EhCacheManager cacheManager;

    /**
     * 后台系统权限缓存
     */
    public static String SYS_PERM_CACHE_NAME = "sysPermissionCache";

    /**
     * 从缓存获取数据
     * @param cacheName
     * @param key
     * @return
     */
    public Object get(String cacheName, String key) {
        return cacheManager.getCache(cacheName).get(key);
    }

    /**
     * 将数据放入缓存
     * @param cacheName
     * @param key
     * @param value
     */
    public void put(String cacheName, String key, Object value) {
        cacheManager.getCache(cacheName).put(key, value);
    }

    /**
     * 移除某个缓存里的某个内容
     * @param cacheName
     * @param key
     */
    public void remove(String cacheName, String key) {
        cacheManager.getCache(cacheName).remove(key);
    }
}
