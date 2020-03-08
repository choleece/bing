package cn.choleece.bing.common.util;

import cn.choleece.bing.common.config.ShiroConfig;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;

import java.nio.charset.Charset;

/**
 * @author choleece
 * @Description: TODO
 * @Date 2020-01-19 21:51
 **/
public class ShiroKeyUtils {
    public static void main(String[] args) {

        String str = new String(Base64.decode("JTAwJTAwJTAwMGttbWg3N2hoZ2dkakhCRg=="));

        String base64 = new String(Base64.decode(new String(Base64.encode(new String("6yhbp;lo9ii8iJHT").getBytes()))));
        System.out.println("---" + str);
        System.out.println(base64);
    }
}
