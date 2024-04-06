package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.PlcFundBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlcFundBaseDao {
    int deleteByPrimaryKey(Integer fundId);

    int insert(PlcFundBase record);

    int insertSelective(PlcFundBase record);

    PlcFundBase selectByPrimaryKey(Integer fundId);

    int updateByPrimaryKeySelective(PlcFundBase record);

    int updateByPrimaryKey(PlcFundBase record);

    List<PlcFundBase> findFunds(List<Integer> list);

    List<PlcFundBase> findFullNameNotEmptyFunds(@Param("pos") int pos, @Param("limitNum") int limitNum);

    List<PlcFundBase> findSaleCodeNotEmptyFunds(@Param("pos") int pos, @Param("limitNum") int limitNum);
}