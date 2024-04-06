package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.vo.process.*;
import cn.com.essence.icbm.sys.enums.OperationTypeEnum;
import cn.com.essence.icbm.sys.service.ProcessService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.rbac.bean.User;
import cn.com.essence.wefa.rbac.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


/**
 * @author: huangll
 * @date: 2021-3-8
 */
@Slf4j
@RestController
@Api(tags = "流程中心模块")
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    //@Autowired
    //private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    /***
     * 模板列表
     * @return
     */
    @GetMapping("/template/list/{pageNo}/{pageSize}")
    @ApiOperation("模板列表")
    @RequiresUser
    public CommonRspVo getTemplates(@PathVariable int pageNo, @PathVariable int pageSize) {
        CommonRspVo rsp = processService.getTemplates(pageNo, pageSize);
        return rsp;
    }
    /***
     * 从流程查询所有模板
     * @return
     */
    @GetMapping("/template/all")
    @ApiOperation("从流程查询所有模板")
    @RequiresUser
    public CommonRspVo getRemoteTemplates() {
        CommonRspVo rsp = processService.getRemoteTemplates();
        return rsp;
    }

    /**
     * 新建模板
     * @return
     */
    @PostMapping("/template")
    @ApiOperation("新建模板")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 流程中心]",operation = "新建模板")
    public CommonRspVo addTemplate(@RequestBody ProcessTemplateReqVo reqVo) {
        CommonRspVo rsp = processService.addTemplate(reqVo);
        return rsp;
    }

    /**
     * 修改模板
     * @param reqVo
     * @return
     */
    @PutMapping("/template")
    @ApiOperation("修改模板")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 流程中心]",operation = "修改模板")
    public CommonRspVo updateTemplate(@RequestBody ProcessTemplateReqVo reqVo) {
        CommonRspVo rsp = processService.updateTemplate(reqVo);
        return rsp;
    }

    /**
     * 新建流程实例
     * @param templateId
     * @return
     */
    @PostMapping("/template/{templateId}/instance")
    @ApiOperation("新建流程实例")
    @RequiresUser
    public CommonRspVo addInstance(@PathVariable String templateId) throws IOException {
        String userId = userService.getCurrentUserId();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        log.info("用户:{},根据模板:{},新建流程实例",userId,templateId);
        return processService.createProcess(templateId, Integer.parseInt(userId), userService.getCurrentUsername());
    }

    /**
     * 流转流程实例
     * @param reqVo
     * @return
     */
    @PostMapping("/instance")
    @ApiOperation("流转流程实例")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 流程中心]",operation = "流转流程实例")
    public CommonRspVo operateInstance(@RequestBody ProcessOperationReqVo reqVo) throws IOException {
        User user = userService.getCurrentUser();
        if (user == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        Map<String, Object> formData = reqVo.getFormData();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(formData));
        JSONArray securityList1 = jsonObject.getJSONArray("securityList");

        if (!CollectionUtils.isEmpty(securityList1)) {
            String categoryId ="";
            String secuIntl ="";
            String status="1";
            for(Object secrityList:securityList1){
                JSONObject jsonObject1 = (JSONObject) secrityList;
                categoryId = jsonObject1.getString("categoryId");
                secuIntl = jsonObject1.getString("secuIntl");
                if(reqVo.getCurrentHandlerNode().indexOf("入池")!=-1){
                    status="2";
                }else if(reqVo.getCurrentHandlerNode().indexOf("出池")!=-1){
                    status="0";
                }
                OperationReqVo operationReqVo=reqVo.getParameters();
                if(operationReqVo.getOperationType().equals(OperationTypeEnum.OPERATION_TYPE_SUBMIT.getName())){
                    //发起请求,更改证券状态为流转中
                    log.info("发起请求,更改证券状态为流转中");
                    processService.updateSecuritiesStatus(categoryId,secuIntl,status);
                }else if(operationReqVo.getOperationType().equals(OperationTypeEnum.OPERATION_TYPE_ABANDON.getName())){
                    //废弃请求,将流转中的证券改为正常
                    log.info("废弃请求,将流转中的证券改为正常");
                    processService.updateSecuritiesStatus(categoryId,secuIntl,status);
                }else if(operationReqVo.getOperationType().equals(OperationTypeEnum.OPERATION_TYPE_DEFAULT_ABANDON.getName())){
                    //自己将流程废弃
                    log.info("自己废弃请求,将流转中的证券改为正常");
                    processService.updateSecuritiesStatus(categoryId,secuIntl, "1");
                }else if (operationReqVo.getOperationType().equals(OperationTypeEnum.OPERATION_TYPE_PASS.getName())&&reqVo.getCurrentHandlerNode().equals("复核审批")||reqVo.getCurrentHandlerNode().equals("审批节点")){
                    //最终审批,将流转中的证券改为正常
                    log.info("最终审批,将流转中的证券改为正常");
                   processService.updateSecuritiesStatus(categoryId,secuIntl,status);
                }
            }
        }
       return processService.operateProcess(reqVo, user);
    }

    /***
     * 流程实例列表
     * @return
     */
    @GetMapping("/instance/list/{pageNo}/{pageSize}")
    @ApiOperation("流程实例列表")
    @RequiresUser
    public CommonRspVo getInstances(ProcessListReqVo reqVo, @PathVariable int pageNo, @PathVariable int pageSize) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        CommonRspVo rsp = processService.getProcessList(reqVo,user.getUsername(), pageNo, pageSize);
        return rsp;
    }

    /***
     * 获取流程实例
     * @return
     */
    @GetMapping("/instance/{processId}")
    @ApiOperation("获取流程实例")
    @RequiresUser
    public CommonRspVo getInstances(@PathVariable String processId) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        CommonRspVo rsp = processService.getProcess(processId, user.getUsername());
        return rsp;
    }
    /**
     * 获取流程模板信息
     *
     */
    @GetMapping("/instance/template/{pageNo}/{pageSize}")
    @ApiOperation("获取流程模板信息")
    @RequiresUser
    public CommonRspVo getTemplate(ProcessTemplate reqVo, @PathVariable int pageNo, @PathVariable int pageSize){
        return processService.getProcesTemplate(reqVo,pageNo,pageSize);
    }
}
