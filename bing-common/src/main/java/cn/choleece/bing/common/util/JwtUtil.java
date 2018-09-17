package cn.choleece.bing.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * jwt util
 * @author choleece
 * @date 2018/9/16
 */
public class JwtUtil {

    /**
     * token 过期时间 5min
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 检查token
     * @param token token
     * @param username 用户名
     * @param secret 密码
     * @return
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 将用户名和密码签名
     * @param username
     * @param secret
     * @return
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username的信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
    }
}
