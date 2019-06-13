package cn.choleece.bing.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * 主要用于登陆解密
 */
public class LoginAesUtil {
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    /**
     * 根据姓名与密文进行解密
     * @param name 姓名
     * @param password 加密过的密码
     * @return
     */
    public static String aesDecrypt(String name, String password) {
        // 进行名字加密，截取0-16位
        String firstKey = SecureUtil.md5(name).substring(0, 16);
        // 根据名字的key，并进行encode编码
        String secondKey = HttpUtil.encode(firstKey,"utf-8");
        // 进行aes解密
        String decryptStr = decrypt(password, secondKey);
        // 根据解密解密结果，进行decode编码
        String sourceStr =HttpUtil.decode(decryptStr,"utf-8");
        return sourceStr;
    }

    public static String aesEncrypt(String name, String password){
        String key=SecureUtil.md5(name).substring(0, 16);
        try {
            String pwd = AesUtil.aesDecrypt(password, key);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 进行aes解密
     * @param sorceStr
     * @param sKey
     * @return
     */
    public static String decrypt(String sorceStr, String sKey) {
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted1 = Base64.decode(sorceStr);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }


}
