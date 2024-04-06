package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysUserNoticeStatus;
import cn.com.essence.icbm.sys.bean.po.SysUserNoticeStatusKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysUserNoticeStatusDao {
    int deleteByPrimaryKey(SysUserNoticeStatusKey key);

    int insert(SysUserNoticeStatus record);

    int insertSelective(SysUserNoticeStatus record);

    SysUserNoticeStatus selectByPrimaryKey(SysUserNoticeStatusKey key);

    int updateByPrimaryKeySelective(SysUserNoticeStatus record);

    int updateByPrimaryKey(SysUserNoticeStatus record);

    int batchInsert(List<SysUserNoticeStatus> list);

    int updateNoticeReadStatus(@Param("noticeIds") List<Integer> noticeIds, @Param("userId") Integer userId, @Param("isRead") String isRead);

    //int updateNoticeDeleteStatus(@Param("noticeIds") List<Integer> noticeIds, @Param("userId") Integer userId, @Param("isDelete") String isDelete);

}