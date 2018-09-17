package cn.choleece.bing.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwt token
 * Created by choleece on 2018/9/15.
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
