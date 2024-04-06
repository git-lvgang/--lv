package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysNotice;
import cn.com.essence.icbm.sys.bean.vo.SysUserNoticeVo;
import cn.com.essence.icbm.sys.bean.po.SysUserNoticeStatus;
import cn.com.essence.icbm.sys.bean.vo.NoticeListReqVo;
import cn.com.essence.icbm.sys.constant.MessageConstant;
import cn.com.essence.icbm.sys.dao.SysNoticeDao;
import cn.com.essence.icbm.sys.dao.SysUserNoticeStatusDao;
import cn.com.essence.icbm.sys.service.NoticeService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: huangll
 * @date: 2021-1-13
 *
 * 通知服务实现类
 */
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private SysUserNoticeStatusDao noticeStatusDao;

    @Autowired
    private SysNoticeDao noticeDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonRspVo add(SysNotice notice) {
        int result = noticeDao.insert(notice);
        if (result == 0) {
            log.error("插入用户通告记录失败");
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }

        log.info("noticeId<{}>", notice.getNoticeId());
        // TODO 获取所有用户ID
        List<Integer>  userIds = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        List<SysUserNoticeStatus> noticeStatusList = new ArrayList<>();
        for(Integer userId : userIds) {
            SysUserNoticeStatus userNoticeStatus = new SysUserNoticeStatus();
            userNoticeStatus.setIsRead(MessageConstant.ISREAD_N);
            userNoticeStatus.setIsTop(MessageConstant.ISTOP_N);
            userNoticeStatus.setNoticeId(notice.getNoticeId());
            userNoticeStatus.setUserId(userId);
            userNoticeStatus.setUpdateTime(notice.getPublishedTime());
            noticeStatusList.add(userNoticeStatus);
        }

        noticeStatusDao.batchInsert(noticeStatusList);

        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    public CommonRspVo update(SysUserNoticeStatus noticeStatus) {
        return null;
    }

    @Override
    public Pager<SysUserNoticeVo> getNotices(NoticeListReqVo req, Integer userId) {
        Pager<SysUserNoticeVo> pager = new Pager<>(req.getPageNo(), req.getPageSize());
        List<String> importances = null;
        List<String> noticeTypes = null;
        if (!Strings.isEmpty(req.getImportances())) {
            importances = Arrays.asList(req.getImportances().split(","));
        }
        if (!Strings.isEmpty(req.getNoticeTypes())) {
            noticeTypes = Arrays.asList(req.getNoticeTypes().split(","));
        }
        pager.addParams("userId", userId);
        pager.addParams("noticeTitle", req.getNoticeTitle());
        pager.addParams("importances", importances);
        pager.addParams("noticeTypes", noticeTypes);
        pager.addParams("isRead", req.getIsRead());
        pager.addParams("startTime", req.getStartTime());
        pager.addParams("endTime", req.getEndTime());
        List<SysUserNoticeVo> notices = noticeDao.findPage(pager);
        log.info("list<{}>", notices);
        pager.setResults(notices);
        return pager;
    }

    /**
     * 批量更新用户通知状态
     * @param noticeIds
     * @param userId
     * @return
     */
//    @Override
//    public CommonRspVo delete(List<Integer> noticeIds, Integer userId) {
//        int result = noticeStatusDao.updateNoticeDeleteStatus(noticeIds, userId, MessageConstant.ISDELETE_Y);
//        if (result == 0) {
//            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
//        }
//        return new CommonRspVo(ResultCode.C_SUCCESS);
//    }

    /**
     * 批量更新用户通告是否已读状态
     * @param noticeIds
     * @param userId
     * @param isRead
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo updateNoticeReadStatus(List<Integer> noticeIds, Integer userId, String isRead) {
        log.info("批量更新用户通告是否已读状态，userId:<{}>，irRead:<{}>，noticeIds:<{}>",userId,isRead,noticeIds);
        int result = noticeStatusDao.updateNoticeReadStatus(noticeIds, userId, isRead);
        if (result == 0) {
            log.info("批量更新失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }
        log.info("批量更新成功：<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    /**
     *
     * @param noticeId
     * @param userId
     * @param isTop
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo updateNoticeTopStatus(Integer noticeId, Integer userId, String isTop) {
        log.info("更新用户通告已读状态，userId:<{}>，isTop:<{}>，noticeId:<{}>",userId,isTop,noticeId);
        SysUserNoticeStatus noticeStatus = new SysUserNoticeStatus();
        noticeStatus.setNoticeId(noticeId);
        noticeStatus.setUserId(userId);
        noticeStatus.setIsTop(isTop);
        int result = noticeStatusDao.updateByPrimaryKeySelective(noticeStatus);
        if(result == 0) {
            log.info("更新失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }
        log.info("更新成功：<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }
}
