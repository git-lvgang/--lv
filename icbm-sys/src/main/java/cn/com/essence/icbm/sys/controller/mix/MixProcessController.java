package cn.com.essence.icbm.sys.controller.mix;

import cn.com.essence.icbm.sys.service.mix.MixProcessService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.rbac.bean.User;
import cn.com.essence.wefa.rbac.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/process")
@Api(tags = "Mix版待办事项")
public class MixProcessController {

    @Autowired
    private MixProcessService processServiceMix;

    @Autowired
    private UserService userService;


    /**
     * Mix工作台待办事项查询
     */
    @GetMapping("/instance/list")
    @RequiresUser
    public CommonRspVo getToDo() {
        User user = userService.getCurrentUser();
        if (user == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        return processServiceMix.getToDo(user.getUsername());
    }
}
