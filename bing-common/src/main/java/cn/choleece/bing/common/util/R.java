package cn.choleece.bing.common.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回类型
 * @author choleece
 * @date 2018/9/27
 */
public class R extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -6287952131441663819L;

    /**
     * 服务器返回成功码
     */
    private static final int SUCCESS_CODE = 0;

    /**
     * 服务器返回失败码
     */
    private static final int ERROR_CODE = -1;

    public R() {
        put("code", SUCCESS_CODE);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(String msg) {
        return error(ERROR_CODE, msg);
    }

    public static R error() {
        return error("服务器开小差了...");
    }

    /**
     * 返回msg & data
     * @param msg
     * @param object
     * @return {"code": 0, "msg": msg, "data": object}
     */
    public static R ok(String msg, Object object) {
        R r = new R();
        r.put("msg", msg);
        r.put("data", object);
        return r;
    }

    /**
     * 返回带data的内容
     * @param object
     * @return {"code": 0, "data": null}
     */
    public static R ok(Object object) {
        R r = new R();
        r.put("data", object);
        return r;
    }

    /**
     * 返回不带msg的结果
     * @return {"code": 0}
     */
    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
