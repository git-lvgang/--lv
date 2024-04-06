package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysSearchIdxDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysSearchIdx record);

    int insertSelective(SysSearchIdx record);

    SysSearchIdx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysSearchIdx record);

    int updateByPrimaryKey(SysSearchIdx record);

    List<SysSearchIdx> selectList(@Param("name") String name, @Param("type") String type, @Param("limitNum") int limitNum);

    List<SysSearchIdx> findByType(@Param("type") String type, @Param("pos") int pos, @Param("limitNum") int limitNum);

    String queryIndexName(@Param("name") String name);

}