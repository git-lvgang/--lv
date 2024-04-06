package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.po.SysFrequentlyFunctionSet;
import cn.com.essence.icbm.sys.service.SysFrequentlyFunctionSetService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Author:Lxy
 */

@RestController
public class SysFrequentlyFunctionSetController {

    @Autowired
    private SysFrequentlyFunctionSetService sysFrequentlyFunctionSetService;
    /**
     * 系统常用功能设置
     * @return
     */
    @PostMapping("/sysset/freqyentlyfunction")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 系统常用菜单设置]",operation = "新增或删除")
    public CommonRspVo sysFreqyentlyFunction(@RequestBody List<SysFrequentlyFunctionSet> sysFrequentlyFunctionSet) {
        /*if(sysFrequentlyFunctionSet == null || sysFrequentlyFunctionSet.size() == 0) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }*/
        CommonRspVo rsp = sysFrequentlyFunctionSetService.sysFreqyentlyFunction(sysFrequentlyFunctionSet);
        return rsp;
    }

    @GetMapping("/sysset/getfreqyentlymenu/{custCode}")
    public CommonRspVo queryFreqyentlyMenu(@PathVariable String custCode) {
        return sysFrequentlyFunctionSetService.getSysFreqyentlyMenu(custCode);
    }

}
