package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysProcessData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysProcessDataDao {
    int deleteByPrimaryKey(String processId);

    int insert(SysProcessData record);

    int insertSelective(SysProcessData record);

    SysProcessData selectByPrimaryKey(String processId);

    int updateByPrimaryKeySelective(SysProcessData record);

    int updateByPrimaryKey(SysProcessData record);

    int countProhibitSecuPollIn(String secuIntl);

    String getSecuPoolSource (String secuIntl);
}