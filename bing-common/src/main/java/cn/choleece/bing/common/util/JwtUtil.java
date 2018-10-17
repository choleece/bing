package cn.choleece.bing.common.util;

import cn.choleece.bing.common.vo.LoginUser;
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
     * jwt 签名凭证
     */
    private static final String JWT_SING_KEY = "bing";

    /**
     * jwt payload 为username key
     */
    private static final String JWT_USER_NAME_KEY = "username";

    /**
     * jwt payload为user key
     */
    private static final String JWT_USER_KEY = "user";

    /**
     * 检查token
     * @param token token
     * @param username 用户名
     * @return
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SING_KEY);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
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
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(JWT_USER_NAME_KEY).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从token中取出LoginUser
     * @param token
     * @return
     */
    public static LoginUser getLoginUser(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String jwtStr = jwt.getClaim(JWT_USER_KEY).asString();
            return GsonUtil.stringToObj(jwtStr, LoginUser.class);
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

        System.out.println("get username from token: " + getUsername(usernameJwt));
        System.out.println("get user from token: " + getLoginUser(userJwt));
        System.out.println("get userId from token: " + getUid(userJwt));
    }
}
