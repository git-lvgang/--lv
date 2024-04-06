package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysProcessFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysProcessFileDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysProcessFile record);

    int insertSelective(SysProcessFile record);

    SysProcessFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysProcessFile record);

    int updateByPrimaryKey(SysProcessFile record);

    List<SysProcessFile> selectByProcessId(@Param("processId") String processId);
}