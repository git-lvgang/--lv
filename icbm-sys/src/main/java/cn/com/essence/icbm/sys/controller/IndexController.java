package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.service.impl.MenuIdxServiceImpl;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huangll
 * @date: 2021-1-27
 *
 * 全局索引生成控制类
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private MenuIdxServiceImpl menuIdxService;

    @PutMapping("/index/menu/trigger")
    @SysLog(moduleName = "[Icbm_Sys Component 菜单栏]",operation = "最终一致性")
    public CommonRspVo generateMenuIdx() {
        int num = menuIdxService.generateIdx();
        log.info("menu modification total(insert and update) count <{}>", num);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

}
