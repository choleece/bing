package cn.choleece.bing.ums.controller;

import cn.choleece.bing.common.util.ShiroUtil;
import cn.choleece.bing.common.vo.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 基础controller
 * @author choleece
 * @date 2018/9/30
 */
public abstract class BaseController {
    protected Logger logger = LogManager.getLogger(BaseController.class);

    /**
     * 获取当前登陆用户信息
     * @return
     */
    protected LoginUser getLoginUser() {
        return ShiroUtil.getUserEntity();
    }

    /**
     * 获取当前登陆用户ID
     * @return
     */
    protected String getUid() {
        return ShiroUtil.getUid();
    }
}