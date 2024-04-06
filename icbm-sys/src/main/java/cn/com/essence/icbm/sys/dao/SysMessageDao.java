package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.MessageInfo;
import cn.com.essence.icbm.sys.bean.po.SysMessage;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysMessageDao {
    int deleteByPrimaryKey(Integer messageId);

    int insert(SysMessage record);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);

    List<SysMessage> findPage(Pager<SysMessage> pager);

    int findPageCount(Pager<SysMessage> pager);

    int updateMessageReadStatus(@Param("messageIds") List<Integer> messageIds, @Param("userId") Integer userId, @Param("isRead") String isRead);

    int updateMessageDeleteStatus(@Param("messageIds") List<Integer> messageIds, @Param("userId") Integer userId, @Param("isDelete") String isDelete);

    MessageInfo getUserMessageInfo(@Param("userId") Integer userId);

    List<SysMessage> findUserMessageWithTimestamp(@Param("userId") Integer userId,@Param("timestamp") Long timestamp,@Param("isRead") String isRead);

}