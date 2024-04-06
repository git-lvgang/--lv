package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.bean.po.SysParamItems;
import cn.com.essence.icbm.sys.service.ParamService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.iam.controller.IamController;
import cn.com.essence.wefa.iam.service.impl.DefaultStoreServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "系统公共参数")
public class SysParamController {

    @Autowired
    private ParamService paramService;


    @Autowired
    DefaultStoreServiceImpl defaultStoreService;

    @Autowired
    IamController iamController;

    @PostMapping("/params/getSysParamService/{pageNo}/{pageSize}")
    @RequiresUser
    @ApiOperation("系统公共参数分页查询")
    public CommonRspVo getSysParamService(@RequestBody SysParam req) {
        CommonListRspVo rsp = paramService.getSysParam(req);
        return rsp;
    }

    @PostMapping("/params/addSysParamService")
    @RequiresUser
    @ApiOperation("系统公共参数新增")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "新增参数")
    public CommonRspVo addSysParamService(@RequestBody SysParam req) {
        CommonRspVo rsp = paramService.addSysParam(req);
        return rsp;
    }

    @PostMapping("/params/updateSysParamService")
    @RequiresUser
    @ApiOperation("系统公共参数修改")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "修改参数")
    public CommonRspVo updateSysParamService(@RequestBody SysParam req) {
        CommonRspVo rsp = paramService.updateSysParam(req);
        return rsp;
    }

    @PostMapping("/params/deleteSysParamService/{paramCode}")
    @RequiresUser
    @ApiOperation("系统公共参数删除")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "删除参数")
    public CommonRspVo deleteSysParamService(@PathVariable String paramCode) {
        CommonRspVo rsp = paramService.deleteSysParam(paramCode);
        return rsp;
    }



    @PostMapping("/params/deleteSysParamItemsService/{paramCode}")
    @RequiresUser
    @ApiOperation("系统公共参数子项删除")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "删除子项参数")
    public CommonRspVo deleteSysParamItemsService(@PathVariable String paramCode) {
        CommonRspVo rsp = paramService.deleteSysParam(paramCode);
        return rsp;
    }

    @PostMapping("/params/updateSysParamItemsService")
    @RequiresUser
    @ApiOperation("系统公共参数子项修改")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "修改子项参数")
    public CommonRspVo updateSysParamItemsService(@RequestBody SysParamItems sysParamItems) {
        CommonRspVo rsp = paramService.updateSysParamItems(sysParamItems);
        return rsp;
    }

    @PostMapping("/params/addSysParamItemsService")
    @RequiresUser
    @ApiOperation("系统公共参数子项新增")
    @SysLog(moduleName = "[Icbm_Sys Component 系统公共参数]",operation = "新增公共参数子项参数")
    public CommonRspVo addSysParamItemsService(@RequestBody SysParamItems sysParamItems) {
        CommonRspVo rsp = paramService.addSysParamItems(sysParamItems);
        return rsp;
    }

    @PostMapping("/params/getSysParamItemsService/{paramCode}")
    @RequiresUser
    @ApiOperation("系统公共参数子项查询")
    public CommonRspVo getSysParamItemsService(@PathVariable String paramCode) {
        CommonRspVo rsp = paramService.getSysParamItems(paramCode);
        return rsp;
    }
}
