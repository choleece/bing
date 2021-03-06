package cn.choleece.bing.common.exceptions;

/**
 *
 * @author choleece
 * @date 2018/9/17
 */
public class RRException extends RuntimeException {

    private static final long serialVersionUID = -2049827065989643787L;

    private String msg;

    private int code = 500;

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
