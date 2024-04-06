package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysNotice;
import cn.com.essence.icbm.sys.bean.vo.SysUserNoticeVo;
import cn.com.essence.icbm.sys.bean.po.SysUserNoticeStatus;
import cn.com.essence.icbm.sys.bean.vo.NoticeListReqVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.mybatis.Pager;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-1-13
 *
 * 通知服务类
 */
public interface NoticeService {
    /**
     *
     * @param notice
     * @return
     */
    CommonRspVo add(SysNotice notice);

    /**
     *
     * @param noticeStatus
     * @return
     */
    CommonRspVo update(SysUserNoticeStatus noticeStatus);

    /**
     *
     */
    Pager<SysUserNoticeVo> getNotices(NoticeListReqVo req, Integer userId);

    /**
     *
     * @param noticeIds
     * @return
     */
//    CommonRspVo delete(List<Integer> noticeIds, Integer userId);

    /**
     * 批量更新用户通告的是否已读状态
     * @param noticeIds
     * @return
     */
    CommonRspVo updateNoticeReadStatus(List<Integer> noticeIds, Integer userId, String isRead);

    /**
     * 更新消息置顶状态
     * @param noticeId
     * @param userId
     * @param isTop
     * @return
     */
    CommonRspVo updateNoticeTopStatus(Integer noticeId, Integer userId, String isTop);
}
