package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.bean.po.SysParamItems;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysParamDao {

    int deleteSysParam(String paramCode);

    int updateSysParam(SysParam sysParam);

    List<SysParam> findSysParamPage(SysParam sysParam);

    int addSysParam(SysParam sysParam);


    int deleteSysParamItems(String paramItemId);

    int updateSysParamItems(SysParamItems sysParamItems);

    List<SysParamItems> findSysParamItems(String paramCode);

    int addSysParamItems(SysParamItems sysParamItems);

}