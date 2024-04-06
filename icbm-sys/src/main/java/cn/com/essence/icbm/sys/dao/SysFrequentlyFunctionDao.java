package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysFrequentlyFunctionSet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author:Lxy
 */

@Mapper
public interface SysFrequentlyFunctionDao {

   int deleteCustSet(String custCode);
   int frequentlyFunction(List<SysFrequentlyFunctionSet> sys);
   List<SysFrequentlyFunctionSet> findSysFreqyentlyMenu(String custCode);
}