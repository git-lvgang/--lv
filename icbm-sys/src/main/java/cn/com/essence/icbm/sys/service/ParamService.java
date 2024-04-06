package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysParam;
import cn.com.essence.icbm.sys.bean.po.SysParamItems;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;


/**
 * Lxy
 */
public interface ParamService {
    /**
     * 新增
     * @param sysParam
     * @return
     */
    CommonRspVo addSysParam(SysParam sysParam);

    /**
     * 修改
     * @param sysParam
     * @return
     */
    CommonRspVo updateSysParam(SysParam sysParam);

    /**
     * 查询
     * @param req
     * @return
     */
    CommonListRspVo getSysParam(SysParam req);

    /**
     * 删除
     * @param paramCode
     * @return
     */
    CommonRspVo deleteSysParam(String paramCode);


    /**
     * 子表新增
     * @param sysParamItems
     * @return
     */
    CommonRspVo addSysParamItems(SysParamItems sysParamItems);

    /**
     * 子表修改
     * @param sysParamItems
     * @return
     */
    CommonRspVo updateSysParamItems(SysParamItems sysParamItems);

    /**
     * 子表查询
     * @param paramCode
     * @return
     */
    CommonListRspVo getSysParamItems(String paramCode);

    /**
     * 子表删除
     * @param paramCode
     * @return
     */
    CommonRspVo deleteSysParamItems(String paramCode);

}
