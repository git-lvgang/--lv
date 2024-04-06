package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.icbm.sys.bean.vo.MessageListReqVo;
import cn.com.essence.icbm.sys.constant.MessageConstant;
import cn.com.essence.icbm.sys.dao.SysMessageDao;
import cn.com.essence.icbm.sys.service.MessageReminderService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: huangll
 * @date: 2021-1-13
 * <p>
 * 消息提醒服务类
 */

@Service
@Slf4j
public class MessageReminderServiceImpl implements MessageReminderService {

    @Autowired
    private SysMessageDao sysMessageDao;

    /**
     * 新增消息
     *
     * @param req
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo add(SysMessage req, Integer userId) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUserId(userId);
        sysMessage.setImportance(req.getImportance());
        sysMessage.setMessageSubject(req.getMessageSubject());
        sysMessage.setMessageContent(req.getMessageContent());
        sysMessage.setCreateTime(new Date());
        sysMessage.setIsDeleted(MessageConstant.ISDELETE_N);
        sysMessage.setIsRead(MessageConstant.ISREAD_N);
        sysMessage.setIsTop(MessageConstant.ISTOP_N);
        sysMessage.setUpdateTime(new Date());
        CommonRspVo rsp = new CommonRspVo();
        int update = sysMessageDao.insertSelective(sysMessage);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        log.info("消息内容：{}，执行结果：{}", sysMessage, 1 == update);
        return rsp;
    }

    /**
     * 批量软删除消息
     *
     * @param messageIds
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo delete(List<Integer> messageIds, Integer userId) {
        log.info("批量软删除信息，用户ID:<{}>，消息IDS:<{}>",userId,messageIds);
        int result = sysMessageDao.updateMessageDeleteStatus(messageIds, userId, MessageConstant.ISDELETE_Y);
        if (result > 0) {
            log.info("删除成功：<{}>",result);
            return new CommonRspVo(ResultCode.C_SUCCESS, "删除成功");
        } else {
            log.info("删除失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "删除失败");
        }
    }

    /**
     * 分页查询消息
     *
     * @param req
     * @param userId
     * @return
     */
    @Override
    public Pager<SysMessage>
    getMessages(MessageListReqVo req, Integer userId) {
        Pager<SysMessage> pager = new Pager<>(req.getPageNo(), req.getPageSize());
        List<String>importances=null;
        if(!Strings.isEmpty(req.getImportance())){
            importances= Arrays.asList(req.getImportance().split(","));
        }
        pager.addParams("userId", userId);
        pager.addParams("subject", req.getSubject());
        pager.addParams("importances",importances);
        pager.addParams("isRead", req.getIsRead());
        pager.addParams("startTime", req.getStartTime());
        pager.addParams("endTime", req.getEndTime());
        List<SysMessage> messages = sysMessageDao.findPage(pager);
        pager.setResults(messages);
        pager.setTotal(pager.getTotal());
        return pager;
    }

    /**
     * 批量更新消息的阅读状态
     *
     * @param messageIds
     * @param userId
     * @param isRead
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo updateMessageReadStatus(List<Integer> messageIds, Integer userId, String isRead) {
        int res = sysMessageDao.updateMessageReadStatus(messageIds, userId, isRead);
        log.info("需要更新的条数<{}>,已经更新的条数为<{}>", messageIds.size(), res);
        return new CommonRspVo(ResultCode.C_SUCCESS, "更新成功");
    }

    /**
     * 更新消息的置顶状态
     *
     * @param messageId
     * @param userId
     * @param isTop
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo updateMessageTopStatus(Integer messageId, Integer userId, String isTop) {
        log.info("messageId:<{}>，userId:<{}>，isTop:<{}>",messageId,userId,isTop);
        SysMessage message = sysMessageDao.selectByPrimaryKey(messageId);
        log.info("message<{}>", message);
        // 数据不存在， 或者已经删除 或者 不是本人的消息
        if (message == null || "1".equals(message.getIsDeleted()) || !userId.equals(message.getUserId())) {
            log.info("数据不存在");
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "数据不存在");
        }

        message = new SysMessage();
        message.setMessageId(messageId);
        message.setIsTop(isTop);
        int res = sysMessageDao.updateByPrimaryKeySelective(message);
        if (res == 0) {
            log.info("更新失败：<{}>",res);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "数据不存在");
        } else {
            log.info("更新成功：<{}>",res);
            return new CommonRspVo(ResultCode.C_SUCCESS, "更新成功");
        }
    }

    @Override
    public MessageInfo getUserMessageInfo(Integer userId, Long timestamp) {
        MessageInfo info = sysMessageDao.getUserMessageInfo(userId);
        if (timestamp != null && timestamp != 0 && info.getLastMessageTimestamp() > timestamp) {
            List<SysMessage> messageList =  sysMessageDao.findUserMessageWithTimestamp(userId,timestamp,"0");
            info.setLastMessageList(messageList);
        }
        return info;
    }
}
