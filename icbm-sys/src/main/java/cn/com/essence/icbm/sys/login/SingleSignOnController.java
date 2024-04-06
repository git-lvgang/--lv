package cn.com.essence.icbm.sys.login;

import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.iam.service.SingleSignOnService;
import cn.com.essence.wefa.rbac.bean.User;
import cn.com.essence.wefa.rbac.common.DefultValidatorService;
import cn.com.essence.wefa.rbac.service.UserService;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
public class SingleSignOnController {

    @Autowired
    private SingleSignOnService singleSignOnService;

    @Autowired
    private DefultValidatorService validator;

    @Autowired
    private UserService userService;

    /**
     * 单点登出接口
     * 1.清理单点登陆的会话资源
     * 2.清理Wefa的会话资源
     *
     * @param request
     * @return
     */
    @PostMapping("/single/logout")
    public CommonRspVo logout(HttpServletRequest request) {
        String ticket = this.getTicket(request);
        log.info("单点登出，Ticket<{}>", ticket);
        Map attributes = singleSignOnService.logout(ticket);
        log.info("单点登出，清理单点登陆Session资源Ticket<{}>,Attributes<{}>", ticket, attributes);
        String oaId = StrUtil.toString(attributes.get("userId"));// userId	用户ID(OaId)	唯一；
        String userId = this.convertToUserId(oaId);
        if (StrUtil.isNotEmpty(userId)) {
            validator.removeToken(userId);
            log.info("单点登出,清理系统内Session资源。Ticket<{}>,OaId<{}>,Uid<{}>", ticket, oaId, userId);
        }
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    private String convertToUserId(String oaId) {
        User user = userService.loadByOaId(oaId);
        return user != null ? user.getUserInfo().getUserId().toString() : null;
    }

    private String getTicket(HttpServletRequest request) {
        String logoutMessage = CommonUtils.safeGetParameter(request, ConfigurationKeys.LOGOUT_PARAMETER_NAME.getDefaultValue(), Collections.singletonList("logoutRequest"));
        log.info("Logout request:\n{}", logoutMessage);
        return XmlUtils.getTextForElement(logoutMessage, "SessionIndex");
    }

}
