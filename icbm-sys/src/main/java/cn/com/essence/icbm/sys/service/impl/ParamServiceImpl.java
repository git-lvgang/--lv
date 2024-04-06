package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.bean.po.SysParamItems;
import cn.com.essence.icbm.sys.dao.SysParamDao;
import cn.com.essence.icbm.sys.service.ParamService;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ParamServiceImpl implements ParamService {

    @Autowired
    private SysParamDao sysParamDao;


    @Override
    @Transactional
    public CommonRspVo updateSysParam(SysParam sysParam) {
        log.info("更改系统参数信息：{}",JSON.toJSONString(sysParam));
        int result = sysParamDao.updateSysParam(sysParam);
        if (result == 0) {
            log.info("更改失败:<{}>",result);
            return new CommonRspVo(ResultCode.C_FAIL);
        }
        log.info("更新成功:<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    public CommonListRspVo getSysParam(SysParam req){
        List<SysParam> sysParamList = sysParamDao.findSysParamPage(req);
        CommonListRspVo rspVo  = new CommonListRspVo();
        rspVo.setRtnCode(ResultCode.C_SUCCESS);
        rspVo.setData(sysParamList);
        return rspVo;
    }

    @Override
    @Transactional
    public CommonRspVo addSysParam(SysParam req){
        log.info("添加系统参数信息：{}",JSON.toJSONString(req));
        int result = sysParamDao.addSysParam(req);
        if (result == 0) {
            log.info("添加失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_FAIL);
        }
        log.info("添加成功:<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    @Transactional
    public CommonRspVo deleteSysParam(String paramCode) {
        log.info("删除系统参数，paramCode:<{}>",paramCode);
        int result = sysParamDao.deleteSysParam(paramCode);
        if (result > 0){
            log.info("删除成功，result:<{}>",result);
            return new CommonRspVo(ResultCode.C_SUCCESS, "系统公共参数删除成功");
        }else{
            log.info("删除失败，result:<{}>",result);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "系统公共参数删除失败");
        }
    }

    @Override
    @Transactional
    public CommonRspVo addSysParamItems(SysParamItems sysParamItems) {
        log.info("添加系统参数信息：{}", JSON.toJSONString(sysParamItems));
        int result = sysParamDao.addSysParamItems(sysParamItems);
        if (result == 0) {
            log.info("添加失败:<{}>",result);
            return new CommonRspVo(ResultCode.C_FAIL);
        }
        log.info("添加成功:<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    @Transactional
    public CommonRspVo updateSysParamItems(SysParamItems sysParamItems) {
        log.info("修改系统参数信息：{}",JSON.toJSONString(sysParamItems));
        int result = sysParamDao.updateSysParamItems(sysParamItems);
        if(result == 0){
            log.info("修改失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_FAIL);
        }
        log.info("修改成功：<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    public CommonListRspVo getSysParamItems(String paramCode) {
        List<SysParamItems> sysParamItemsList = sysParamDao.findSysParamItems(paramCode);
        CommonListRspVo rspVo  = new CommonListRspVo();
        rspVo.setData(sysParamItemsList);
        rspVo.setRtnCode(ResultCode.C_SUCCESS);
        log.info("list<{}>", sysParamItemsList);
        return rspVo;
    }

    @Override
    @Transactional
    public CommonRspVo deleteSysParamItems(String paramCode) {
        log.info("paramCode：<{}>",paramCode);
        int result = sysParamDao.deleteSysParamItems(paramCode);
        if (result > 0){
            log.info("系统公共参数子项删除成功：<{}>",result);
            return new CommonRspVo(ResultCode.C_SUCCESS, "系统公共参数子项删除成功");
        }else{
            log.info("系统公共参数子项删除失败：<{}>",result);
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "系统公共参数子项删除失败");
        }
    }
}
