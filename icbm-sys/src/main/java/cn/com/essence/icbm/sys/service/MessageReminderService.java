package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.bean.vo.MessageListReqVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.mybatis.Pager;

import java.util.List;

public interface MessageReminderService {

    /**
     * 新增消息
     *
     * @param req
     * @param userId
     * @return
     */
    CommonRspVo add(SysMessage req, Integer userId);

    /**
     * 批量软删除消息
     *
     * @param messageIds
     * @param userId
     * @return
     */
    CommonRspVo delete(List<Integer> messageIds, Integer userId);

    /**
     * 分页查询消息
     *
     * @param req
     * @param userId
     * @return
     */
    Pager<SysMessage> getMessages(MessageListReqVo req, Integer userId);

    /**
     * 批量更新消息的是否已读状态
     *
     * @param messageIds
     * @return
     */
    CommonRspVo updateMessageReadStatus(List<Integer> messageIds, Integer userId, String isRead);

    /**
     * 更新消息置顶状态
     *
     * @param messageId
     * @param userId
     * @param isTop
     * @return
     */
    CommonRspVo updateMessageTopStatus(Integer messageId, Integer userId, String isTop);

    /**
     * 获取指定用户的消息概览
     *
     * @param userId    用户ID
     * @param timestamp 上一次查询消息概览的时间
     * @return
     */
    MessageInfo getUserMessageInfo(Integer userId, Long timestamp);

}
