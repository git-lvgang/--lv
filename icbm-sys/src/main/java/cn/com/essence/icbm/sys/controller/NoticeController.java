package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.po.SysNotice;
import cn.com.essence.icbm.sys.bean.vo.NoticeListReqVo;
import cn.com.essence.icbm.sys.bean.vo.NoticeReqVo;
import cn.com.essence.icbm.sys.bean.vo.SysUserNoticeVo;
import cn.com.essence.icbm.sys.constant.MessageConstant;
import cn.com.essence.icbm.sys.service.NoticeService;
import cn.com.essence.icbm.sys.service.UserInfoService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService service;

    @Autowired
    private UserInfoService userInfoService;

    // TODO 通告发送应该要有权限的人才能发的
    @PostMapping("/notice")
    @RequiresUser
    public CommonRspVo addNotice(@RequestBody NoticeReqVo req) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }

        SysNotice notice = new SysNotice();
        notice.setNoticeType(req.getNoticeType());
        notice.setImportance(req.getImportance());
        notice.setNoticeTitle(req.getNoticeTitle());
        notice.setNoticeContent(req.getNoticeContent());
        notice.setNoticeSummary(req.getNoticeSummary());
        // TODO 附件怎么处理
        //notice.setNoticeAppendix(req.get);
        notice.setIsDeleted(MessageConstant.ISDELETE_N);
        notice.setNoticeSenderId(Math.toIntExact(userId));
        notice.setPublishedTime(new Date());


        CommonRspVo rsp = service.add(notice);
        return rsp;
    }

    /**
     * 批量将用户通知公告改为已读
     * @return
     */
    @PutMapping("/notice/read")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 通知公告]",operation = "修改公告为已读")
    //@ApiOperation("1111")
    //@ApiImplicitParam(paramType = "path", name = "userId", dataType = "List", required = true, value = "用户Id")
    public CommonRspVo updateUserNoticeReadStatus(@RequestBody List<Integer> noticeIds) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }
        return service.updateNoticeReadStatus(noticeIds, Math.toIntExact(userId), MessageConstant.ISREAD_Y);
    }

    /**
     * 修改用户通知公告置顶状态
     * status = 0  取消置顶
     * status = 1  置顶
     * @return
     */
    @PutMapping("/notice/top/{noticeId}/{status}")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 通知公告]",operation = "修改公告为置顶")
    //@ApiOperation("1111")
    //@ApiImplicitParam(paramType = "path", name = "userId", dataType = "List", required = true, value = "用户Id")
    public CommonRspVo updateMessageTopStatus(@PathVariable Integer noticeId, @PathVariable String status) {
        // 校验入参
        if (!(MessageConstant.ISTOP_Y.equals(status) || MessageConstant.ISTOP_N.equals(status))) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }

        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }

        return service.updateNoticeTopStatus(noticeId, Math.toIntExact(userId), status);
    }

    /**
     * 批量将用户通告删除
     * @return
     */
//    @DeleteMapping("/notice")
//    @RequiresUser
//    public CommonRspVo deleteMessage(@RequestBody List<Integer> noticeIds) {
//        // TODO 是不是要有检验
//        Long userId = ShiroUtils.getUserId();
//        if (userId == null) {
//            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
//        }
//
//        return service.delete(noticeIds, Math.toIntExact(userId));
//    }


    /**
     * 查询当前用户通告列表(分页)
     * @param req
     * @return
     */
    @GetMapping("/notice/list")
    @RequiresUser
    public CommonRspVo getNotices(NoticeListReqVo req) {
        Integer userId = userInfoService.getCurrentUser();
        if (userId == null) {
            return new CommonRspVo(ResultCode.C_NOT_AUTHZ);
        }

        CommonListRspVo rsp = new CommonListRspVo();

        Pager<SysUserNoticeVo> pager = service.getNotices(req, Math.toIntExact(userId));
        rsp.setTotal(pager.getTotal());
        rsp.setData(pager.getResults());
        rsp.setRtnCode(ResultCode.C_SUCCESS);

        return rsp;
    }
}
