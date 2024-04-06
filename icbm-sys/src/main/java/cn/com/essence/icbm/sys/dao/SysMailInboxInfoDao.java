package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysMailInboxInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysMailInboxInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMailInboxInfo record);

    int insertSelective(SysMailInboxInfo record);

    SysMailInboxInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMailInboxInfo record);

    int updateByPrimaryKey(SysMailInboxInfo record);

    List<SysMailInboxInfo> selectMails(@Param("startDate") Date startDate, @Param("receiver") String receiver);

    Date selectMaxReceiverTime(@Param("receiver") String receiver);

    List<String> selectMailUids(@Param("startDate") Date startDate, @Param("receiver") String receiver, @Param("senderMailBoxes") List<String> senderMailBoxes);
}