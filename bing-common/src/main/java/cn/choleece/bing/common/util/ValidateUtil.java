package cn.choleece.bing.common.util;

import java.util.regex.Pattern;

/**
 * 验证工具
 */
public class ValidateUtil {

    /**
     * 密码复杂度校验，10-18位，必须包含小写、大写、数字、特殊字符至少三种
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?:(?=.*[a-z])(?:(?=.*[A-Z])(?=.*[\\d\\W])|(?=.*\\W)(?=.*\\d))|(?=.*\\W)(?=.*[A-Z])(?=.*\\d)).{6,18}$");
        return pattern.matcher(password).matches();
    }

    public static void main(String[] args) {
        System.out.println(isValidPassword("ZaqXswCde1"));
    }
}
