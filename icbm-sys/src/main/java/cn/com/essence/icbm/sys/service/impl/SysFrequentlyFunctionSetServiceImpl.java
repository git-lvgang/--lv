package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysFrequentlyFunctionSet;
import cn.com.essence.icbm.sys.dao.SysFrequentlyFunctionDao;
import cn.com.essence.icbm.sys.service.SysFrequentlyFunctionSetService;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.shiro.util.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Author:Lxy
 */

@Service
@Slf4j
public class SysFrequentlyFunctionSetServiceImpl implements SysFrequentlyFunctionSetService {

    @Autowired
    private SysFrequentlyFunctionDao sysFrequentlyFunctionDao;

    @Override
    public CommonRspVo sysFreqyentlyFunction(List<SysFrequentlyFunctionSet> sysFrequentlyFunctionSet) {
        String custCode = ShiroUtils.getCurrentUserId();
        if(!StringUtils.isEmpty(custCode)){
            sysFrequentlyFunctionDao.deleteCustSet(custCode);
        }
        if(sysFrequentlyFunctionSet.size() > 0) {
            sysFrequentlyFunctionDao.frequentlyFunction(sysFrequentlyFunctionSet);
        }
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    public CommonListRspVo getSysFreqyentlyMenu(String custCode) {
        List<SysFrequentlyFunctionSet> sysfreList = sysFrequentlyFunctionDao.findSysFreqyentlyMenu(custCode);
        CommonListRspVo rspVo  = new CommonListRspVo();
        rspVo.setData(sysfreList);
        rspVo.setRtnCode(ResultCode.C_SUCCESS);
        return rspVo;
    }

}
