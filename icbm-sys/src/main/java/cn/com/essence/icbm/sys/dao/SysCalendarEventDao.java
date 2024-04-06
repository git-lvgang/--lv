package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysCalendarEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysCalendarEventDao {
    int deleteByPrimaryKey(Integer eventId);

    int insert(SysCalendarEvent record);

    int insertSelective(SysCalendarEvent record);

    SysCalendarEvent selectByPrimaryKey(Integer eventId);

    int updateByPrimaryKeySelective(SysCalendarEvent record);

    int updateByPrimaryKey(SysCalendarEvent record);

    List<SysCalendarEvent> selectEvents(@Param("params")Map<String, Object> map);
}