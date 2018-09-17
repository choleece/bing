package cn.choleece.bing.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @description: google gson util
 * @author: choleece
 * @date: 2018-01-18
 */
public class GsonUtil {

    /**
     * string to Java object
     * @param str
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T stringToObj(String str, Class<T> classOfT) {
        return new Gson().fromJson(str, (Type)classOfT);
    }

    /**
     * string to list of Java object
     * @param str
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToList(String str) {
        return new Gson().fromJson(str, new TypeToken<List<T>>() {}.getType());
    }
}
