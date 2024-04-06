package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysFrequentlyFunctionSet;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;

import java.util.List;


/**
 * Author:Lxy
 */
public interface SysFrequentlyFunctionSetService {

    CommonRspVo sysFreqyentlyFunction(List<SysFrequentlyFunctionSet> sysFrequentlyFunctionSet);

    CommonListRspVo getSysFreqyentlyMenu(String custCode);
}
