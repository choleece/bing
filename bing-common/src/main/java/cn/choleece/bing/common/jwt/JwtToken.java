package cn.choleece.bing.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwt token
 *
 * @author choleece
 * @date 2018/9/15
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = -8129821562779712501L;

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
