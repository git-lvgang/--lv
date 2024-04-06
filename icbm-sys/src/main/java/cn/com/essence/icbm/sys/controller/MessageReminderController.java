package cn.com.essence.icbm.sys.controller;

import java.util.ArrayList;
import java.util.List;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.bean.vo.MessageListReqVo;
import cn.com.essence.icbm.sys.constant.MessageConstant;
import cn.com.essence.icbm.sys.service.MessageReminderService;
import cn.com.essence.icbm.sys.service.UserInfoService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageReminderController {

    @Autowired
    private MessageReminderService service;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/message")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 消息提醒]",operation = "新增")
    public CommonRspVo addMessage(@RequestBody SysMessage msg) {
        Integer userId = msg.getUserId();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR,"缺少必要参数[userId]");
        }
        CommonRspVo rsp = service.add(msg, Math.toIntExact(userId));
        return rsp;
    }

    /**
     * 批量将消息改为已读
     *
     * @return
     */
    @PutMapping("/message/read")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 消息提醒]",operation = "批量已读")
    //@ApiOperation("1111")
    //@ApiImplicitParam(paramType = "path", name = "userId", dataType = "List", required = true, value = "用户Id")
    public CommonRspVo updateMessageReadStatus(@RequestBody List<Integer> messageIds) {
        if (messageIds == null || messageIds.size() == 0) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }

        Integer userId = userInfoService.getCurrentUser();

        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        return service.updateMessageReadStatus(messageIds, Math.toIntExact(userId), MessageConstant.ISREAD_Y);
    }

    /**
     * 修改消息置顶状态
     * status = 0  取消置顶
     * status = 1  置顶
     *
     * @return
     */
    @PutMapping("/message/top/{messageId}/{status}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 消息提醒]",operation = "修改消息置顶")
    //@ApiOperation("1111")
    //@ApiImplicitParam(paramType = "path", name = "userId", dataType = "List", required = true, value = "用户Id")
    public CommonRspVo updateMessageTopStatus(@PathVariable Integer messageId, @PathVariable String status) {
        // 校验入参
        if (!(MessageConstant.ISTOP_Y.equals(status) || MessageConstant.ISTOP_N.equals(status))) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }

        Integer userId = userInfoService.getCurrentUser();

        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        return service.updateMessageTopStatus(messageId, Math.toIntExact(userId), status);
    }

    /**
     * 批量将消息将消息删除
     *
     * @return
     */
    @DeleteMapping("/message")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 消息提醒]",operation = "批量删除消息")
    public CommonRspVo deleteMessage(@RequestParam("messageIds") String messageIds) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        List<Integer> messageIdList = new ArrayList<>();
        for (String messageId : messageIds.split(",")) {
            messageIdList.add(Integer.valueOf(messageId));
        }
        return service.delete(messageIdList, userId);
    }


    /**
     * 查询当前用户的消息列表(分页)
     *
     * @param req
     * @return
     */
    @GetMapping("/message/list")
    @RequiresUser
    public CommonRspVo getMessages(MessageListReqVo req) {
        Integer userId = userInfoService.getCurrentUser();

        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        CommonListRspVo rsp = new CommonListRspVo();

        Pager<SysMessage> pager = service.getMessages(req, Math.toIntExact(userId));
        rsp.setTotal(pager.getTotal());
        rsp.setData(pager.getResults());
        rsp.setRtnCode(ResultCode.C_SUCCESS);

        return rsp;
    }

    /**
     * 获取消息概要
     *
     * @return
     */
    @GetMapping("/message/info")
    @RequiresUser
    public CommonRspVo info(@RequestParam(value = "timestamp", required = false) Long timestamp) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        MessageInfo data = service.getUserMessageInfo(userId, timestamp);
        return new CommonRspVo(ResultCode.C_SUCCESS, data);
    }
}
