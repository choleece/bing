package cn.choleece.bing.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * 密码工具
 * @author choleece
 * @date 2018/9/27
 */
public class PwdUtil {

    /**
     * 生成密码盐
     * @return
     */
    public static String genSalt() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    /**
     * 加盐生成密码
     * @param pwd
     * @param salt
     * @return
     */
    public static String genPwd(String pwd, String salt) {
        return new Md5Hash(pwd, salt).toString();
    }

    /**
     * 不加盐生成密码
     * @param pwd
     * @return
     */
    public static String genPwd(String pwd) {
        return new Md5Hash(pwd).toString();
    }
}
