package cn.choleece.bing.common.util;

import cn.choleece.bing.common.vo.LoginUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * jwt util
 * @author choleece
 * @date 2018/9/16
 */
public class JwtUtil {

    private static final Logger logger = LogManager.getLogger(JwtUtil.class);

    /**
     * token 过期时间 12h
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * jwt 签名凭证
     */
    private static final String JWT_SING_KEY = "bing";

    /**
     * jwt payload 为username key
     */
    public static final String JWT_USER_NAME_KEY = "username";

    /**
     * jwt payload为user key
     */
    public static final String JWT_USER_KEY = "user";

    /**
     * 检查token
     * @param token token
     * @return
     */
    public static boolean verify(String token, String keyName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(keyName, getJwtPayload(token, keyName)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            LogUtil.error(logger, "verify token exception", e.getMessage());
            return false;
        }
    }

    public static String sign(String keyName, String value) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(JWT_SING_KEY);
        return JWT.create().withClaim(keyName, value).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 将用户名信息放入payload签名
     * @param username
     * @return
     */
    public static String sign(String username) {
        return sign(JWT_USER_NAME_KEY, username);
    }

    /**
     * 将LoginUser信息放入payload
     * @param user
     * @return
     */
    public static String sign(LoginUser user) {
        return sign(JWT_USER_KEY, GsonUtil.objToString(user, LoginUser.class));
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getJwtPayload(token, JWT_USER_NAME_KEY);
    }

    /**
     * 从token中取出LoginUser
     * @param token
     * @return
     */
    public static LoginUser getLoginUser(String token) {
        String str = getJwtPayload(token, JWT_USER_KEY);
        if (str != null) {
            return GsonUtil.stringToObj(str, LoginUser.class);
        }
        return null;
    }

    /**
     * 获取负载信息
     * @param token
     * @param keyName
     * @return
     */
    public static String getJwtPayload(String token, String keyName) {
        try {
            return JWT.decode(token).getClaim(keyName).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从token中取出uid
     * @param token
     * @return
     */
    public static String getUid(String token) {
        LoginUser user = getLoginUser(token);
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    public static void main(String[] args) {
        String username = "choleece";
        String uid = "1";
        LoginUser user = new LoginUser();
        user.setUsername(username);
        user.setUid(uid);

        String usernameJwt = sign(username);
        String userJwt = sign(user);

        System.out.println("username jwt: " + usernameJwt);
        System.out.println("user jwt: " + userJwt);

        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDI1MTc2MDcsInVzZXIiOiJ7XCJ1aWRcIjpcIjFcIixcInVzZXJuYW1lXCI6XCJjaG9sZWVjZVwifSJ9.KBEy76Oj1vgzLOxPLhIAi0B15WI3neDYQwVKMWuGgsw";

        LoginUser user1 = new LoginUser();
        user1.setUid("2");
        user.setUsername("choleece");

        System.out.println(verify(jwt, JWT_USER_KEY));

        System.out.println("get username from token: " + getUsername(usernameJwt));
        System.out.println("get user from token: " + getLoginUser(userJwt));
        System.out.println("get userId from token: " + getUid(userJwt));
    }
}
