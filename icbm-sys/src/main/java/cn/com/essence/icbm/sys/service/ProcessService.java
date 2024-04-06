package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.vo.process.ProcessListReqVo;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessOperationReqVo;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessTemplate;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessTemplateReqVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.rbac.bean.User;

import java.io.IOException;


/**
 * @author: huangll
 * @date: 2021-3-10
 */
public interface ProcessService {
    /**
     * 获取模板列表
     * @return
     */
    CommonRspVo getTemplates(int pageNo, int pageSize);

    /**
     * 获取远程模板列表
     * @return
     */
    CommonRspVo getRemoteTemplates();

    /**
     * 更新模板
     * @param reqVo
     * @return
     */
    CommonRspVo updateTemplate(ProcessTemplateReqVo reqVo);

    /**
     * 新增模板
     * @param reqVo
     * @return
     */
    CommonRspVo addTemplate(ProcessTemplateReqVo reqVo);


    /**
     * 新增流程
     * @param templateId
     * @return
     */
    CommonRspVo createProcess(String templateId, int userId, String userName) throws IOException;

    /**
     * 操作流程
     * @param reqVo
     * @param user
     * @return
     */
    CommonRspVo operateProcess(ProcessOperationReqVo reqVo, User user);

    /**
     * 获取流程列表
     * @param reqVo
     * @param userName
     * @param pageNo
     * @param pageSize
     * @return
     */
    CommonRspVo getProcessList(ProcessListReqVo reqVo, String userName, int pageNo, int pageSize);

    /**
     * 获取流程实例
     * @param processId
     * @return
     */
    CommonRspVo getProcess(String processId, String userName);

    /***
     *获取流程模板信息
     */
    CommonRspVo getProcesTemplate(ProcessTemplate reqVo, int pageNo, int pageSize);

    /*
    *
    *修改证券状态
    */
    CommonRspVo updateSecuritiesStatus(String categoryId,String secuIntl,String status);
}
