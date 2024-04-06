package cn.com.essence.icbm.sys.service.impl;


import cn.com.essence.icbm.sys.bean.po.SysProcessData;
import cn.com.essence.icbm.sys.bean.po.SysProcessFile;
import cn.com.essence.icbm.sys.bean.po.SysProcessTemplate;
import cn.com.essence.icbm.sys.bean.vo.FileInfo;
import cn.com.essence.icbm.sys.bean.vo.process.*;
import cn.com.essence.icbm.sys.dao.SysProcessDataDao;
import cn.com.essence.icbm.sys.dao.SysProcessFileDao;
import cn.com.essence.icbm.sys.dao.SysProcessTemplateDao;
import cn.com.essence.icbm.sys.enums.MarketEnum;
import cn.com.essence.icbm.sys.enums.SecuPoolEnum;
import cn.com.essence.icbm.sys.remote.ProcessApi;
import cn.com.essence.icbm.sys.remote.ProcessStringApi;
import cn.com.essence.icbm.sys.service.ProcessService;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.com.essence.wefa.core.mybatis.Pager;
import cn.com.essence.wefa.rbac.bean.User;
import cn.com.essence.wefa.util.IDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: huangll
 * @date: 2021-3-10
 */
@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    public final String regular = "^([\\u4E00-\\uFA29]|[\\uE7C7-\\uE7F3]|[a-zA-Z0-9_]|[\\!\\_\\*\\.\\-\\'\\(\\)])+$";

    @Autowired
    private SysProcessTemplateDao templateDao;

    @Autowired
    private ProcessApi processApi;

    @Autowired
    private ProcessStringApi processStringApi;

    @Autowired
    private SysProcessDataDao processDataDao;

    @Autowired
    private SysProcessTemplateDao sysProcessTemplateDao;

    @Autowired
    private SysProcessFileDao processFileDao;

    @Value("${essence.ambs.basic.process-label}")
    private String labelName;

    @Override
    public CommonRspVo getTemplates(int pageNo, int pageSize) {
        Pager<SysProcessTemplate> pager = new Pager<SysProcessTemplate>(pageNo, pageSize);
        List<SysProcessTemplate> templateList = templateDao.findPage(pager);
        CommonListRspVo rsp = new CommonListRspVo(ResultCode.C_SUCCESS);
        rsp.setData(templateList);
        rsp.setTotal(pager.getTotal());
        return rsp;
    }

    @Override
    public CommonRspVo getRemoteTemplates() {
        TemplateParam param = new TemplateParam();
        // TODO 先写死
        param.setOffset(0);
        param.setPageSize(200);
        param.setLabelName(labelName);
        List<ProcessTemplateVo> list = processApi.getTemplates(param);
        List<ProcessTemplate> templateList = new ArrayList<>();
        for (ProcessTemplateVo t : list) {
            String name = templateDao.queryTemplateName(t.getFdId());
            if (name != null) {
                templateList.add(new ProcessTemplate(t.getFdId(), name));
            }
        }
        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
        rsp.setData(templateList);
        return rsp;
    }

    @Override
    @Transactional
    public CommonRspVo updateTemplate(ProcessTemplateReqVo reqVo) {
        log.info("修改流程模板信息：{}", JSON.toJSONString(reqVo));
        SysProcessTemplate template = templateDao.selectByPrimaryKey(reqVo.getTemplateId());
        if (template == null) {
            log.info("流程模板不存在：{}", JSON.toJSONString(template));
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND);
        }
        String teplateName = templateDao.queryTemplateName(reqVo.getTemplateId());
        if (!teplateName.equals(reqVo.getTemplateName())) {
            return new CommonRspVo(ResultCode.C_FAIL, "修改失败，" + reqVo.getTemplateName() + "已存在!");
        }
        template = new SysProcessTemplate();
        template.setTemplateId(reqVo.getTemplateId());
        template.setTemplateName(reqVo.getTemplateName());
        template.setViewPath(reqVo.getViewPath());
        template.setEditPath(reqVo.getEditPath());
        template.setDescribe(reqVo.getDescribe());
        template.setRemark(reqVo.getRemark());
        template.setUpdateTime(new Date());
        template.setStatus(reqVo.getStatus());
        template.setModificationNode(reqVo.getModificationNode());
        templateDao.updateByPrimaryKeySelective(template);
        log.info("流程模板修改成功");
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    @Transactional
    public CommonRspVo addTemplate(ProcessTemplateReqVo reqVo) {
        log.info("新建流程模板信息为：{}", JSON.toJSONString(reqVo));
        SysProcessTemplate template = new SysProcessTemplate();
        template.setTemplateId(reqVo.getTemplateId());
        template.setTemplateName(reqVo.getTemplateName());
        template.setViewPath(reqVo.getViewPath());
        template.setEditPath(reqVo.getEditPath());
        template.setDescribe(reqVo.getDescribe());
        template.setRemark(reqVo.getRemark());
        Date date = new Date();
        template.setCreateTime(date);
        template.setUpdateTime(date);
        template.setModificationNode(reqVo.getModificationNode());
        template.setStatus("1");
        String name = templateDao.queryTemplateName(template.getTemplateId());
        CommonRspVo rsp = new CommonRspVo();
        if (name != null) {
            rsp.setRtnCode(ResultCode.C_FAIL, name + "流程模板已存在!");
            return rsp;
        }
        templateDao.insert(template);
        log.info("流程模板添加成功");
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    /**
     * 新建流程
     *
     * @param templateId
     * @return
     */
    @Override
    @Transactional
    public CommonRspVo createProcess(String templateId, int userId, String userName) {
        String formInstanceId = IDGenerator.generateID();
        ProcessCreateVo processCreateVO = new ProcessCreateVo();
        processCreateVO.setTemplateId(templateId);
        processCreateVO.setFormInstanceId(formInstanceId);
        processCreateVO.setLoginName(userName);
        log.info("新建流程参数为:{}", JSON.toJSONString(processCreateVO));
        String processId;
        try {
            processId = processStringApi.createProcess(processCreateVO);
        } catch (Exception e) {
            log.error("新建流程失败", e);
            return new CommonRspVo("创建流程失败");
        }

        log.info("新建流程ID为:{}", processId);

        SysProcessData processData = new SysProcessData();
        processData.setProcessId(processId);
        processData.setFormId(formInstanceId);
        processData.setUserId(userId);
        processData.setTemplateId(templateId);
        processData.setCreateTime(new Date());
        //流程表新增记录
        processDataDao.insert(processData);

        ProcessFormVo formVo = new ProcessFormVo();
        formVo.setProcessId(processId);
        formVo.setLoginName(userName);
        formVo.setFormInstanceId(formInstanceId);

        log.info("查询流程参数为:{}", JSON.toJSONString(formVo));
        //查看流程
        ProcessInfo processInfo = processApi.getProcess(formVo);
        log.info("查询流程结果为:{}", JSON.toJSONString(processInfo));
        //String currentNode = processInfo.getCurrentHandlers().get(0).getNodeInfo().getName();
        String currentNode = processInfo.getFdTaskInfo().getHandlerInfos().get(0).getNodeName();

        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
        Map<String, String> map = new HashMap<>();
        map.put("processId", processId);
        map.put("formId", formInstanceId);
        map.put("currentHandlerNode", currentNode);
        rsp.setData(map);
        return rsp;
    }

    @Override
    @Transactional
    public CommonRspVo operateProcess(ProcessOperationReqVo reqVo, User user) {
        log.debug("processOperationReq<{}>", reqVo);
        String processId = reqVo.getParameters().getProcessId();
        CommonRspVo rsp = new CommonRspVo();
        if (StringUtils.isBlank(processId)) {
            rsp.setRtnCode(ResultCode.C_PARAMS_ERROR, "流程实例不能为空");
            return rsp;
        }
        OperationReqVo operationParams = reqVo.getParameters();
        operationParams.setLoginName(user.getUsername());
        SysProcessData processData = new SysProcessData();
        processData.setProcessId(processId);

        Map<String, Object> formData = reqVo.getFormData();

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(formData));
        JSONArray securityList1 = jsonObject.getJSONArray("securityList");
        if (!CollectionUtils.isEmpty(securityList1)) {
            JSONObject jsonObject1 = securityList1.getJSONObject(0);
            String secuCode = jsonObject1.getString("secuCode");
            if (StringUtils.isBlank(secuCode)) {
                rsp.setRtnCode(ResultCode.C_PARAMS_ERROR, "证券代码不能为空");
                return rsp;
            }

            String secuIntl = jsonObject1.getString("secuIntl");
            if (StringUtils.isBlank(secuIntl)) {
                String market = jsonObject1.getString("market");
                secuIntl = MarketEnum.getSuffix(market, secuCode);
            }

                 //判断是否是禁止池入池请求
                if (SecuPoolEnum.PROHIBIT_SECU_POOL_IN.getName().equals(reqVo.getCurrentHandlerNode())) {
                    int countProhibitSecuPoll = processDataDao.countProhibitSecuPollIn(secuIntl);
                    if (countProhibitSecuPoll > 0) {
                        rsp.setRtnCode(ResultCode.C_FAIL, "禁止池已存在此代码");
                        return rsp;
                    }
                }

                //判断是否是 万德数据或者隔离墙的禁止数据
                if (SecuPoolEnum.PROHIBIT_SECU_POOL_OUT.getName().equals(reqVo.getCurrentHandlerNode())) {
                    String source = processDataDao.getSecuPoolSource(secuIntl);
                    if (SecuPoolEnum.SECU_POOL_SOURCE_QUARANTINECHWALL.getCode().equals(source) ||
                            SecuPoolEnum.SECU_POOL_SOURCE_WINDCHWALL.getCode().equals(source)) {
                        rsp.setRtnCode(ResultCode.C_FAIL, "此证券代码为chwall数据，不允许操作");
                        return rsp;
                    }
                }


            JSONArray attachments1 = jsonObject1.getJSONArray("attachments");
            if (!CollectionUtils.isEmpty(attachments1)) {
                List<String> pools = Lists.newArrayListWithCapacity(attachments1.size());
                for (Object o : attachments1) {
                    JSONObject jsonObject2 = (JSONObject) o;
                    String fileName = jsonObject2.getString("fileName");
                    pools.add(fileName);
                }
                boolean allMatch = pools.stream().allMatch(s -> s.matches(regular));
                if (!allMatch) {
                    rsp.setRtnCode(ResultCode.C_FAIL, "文件名只允许中文、字母、数字，英文括号()以及特殊字符! - _ . * '");
                    return rsp;
                }
            }
        }
        String name = jsonObject.getString("fileName");
        if (name != null) {
            boolean flag = name.matches(regular);
            if (!flag) {
                rsp.setRtnCode(ResultCode.C_FAIL, "文件名只允许中文、字母、数字，英文括号()以及特殊字符! - _ . * '");
                return rsp;
            }
        }

        if (formData != null) {
            processData.setFormData(JSON.toJSONString(formData));
            processData.setSubject(reqVo.getParameters().getSubject());
        }
        // TODO文件先不处理
        processDataDao.updateByPrimaryKeySelective(processData);
        List<FileInfo> fileList = reqVo.getFileList();
        SysProcessFile processFile = new SysProcessFile();
        processFile.setProcessId(processData.getProcessId());
        processFile.setHandlerNode(reqVo.getCurrentHandlerNode());
        processFile.setHandler(user.getUsername());
        processFile.setHandlerId(Math.toIntExact(user.getId()));
        processFile.setCreateTime(new Date());
        processFile.setHandlerName(user.getUserInfo().getName());
        if (fileList != null && fileList.size() > 0) {
            processFile.setFileList(JSON.toJSONString(fileList));
        }
        if (!StringUtils.isEmpty(reqVo.getCurrentHandlerNode()) || !StringUtils.isEmpty(processFile.getFileList())) {
            // 只要文件不为空 或者 当前节点不为空
            processFileDao.insert(processFile);
        }

        log.info("params:<{}>", operationParams);
        processStringApi.execute(operationParams);

        // TODO 先去流程中心查流程实例

        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }

    @Override
    public CommonRspVo getProcessList(ProcessListReqVo reqVo, String userName, int pageNo, int pageSize) {
        ProcessListParamVo paramVO = new ProcessListParamVo();
        paramVO.setOffset((pageNo - 1) * pageSize);
        paramVO.setPageSize(pageSize);
        paramVO.setStatus(reqVo.getStatus());
        paramVO.setMydoc(reqVo.getMydoc());
        paramVO.setTemplateIds(reqVo.getTemplateIds());
        if (StringUtils.isEmpty(reqVo.getTemplateIds())) {
            // 如果为空就要传
            paramVO.setLabelName(labelName);
        }
        if (StringUtils.isEmpty(reqVo.getMydoc())) {
            paramVO.setMydoc("myAll");
        }
        paramVO.setFdSubject(reqVo.getSubject());
        //paramVO.setFdCreator("");
        if (reqVo.getLastHandleBeginTime() != null) {
            paramVO.setLastHandleBeginTime(reqVo.getLastHandleBeginTime().getTime());
        }
        if (reqVo.getLastHandleEndTime() != null) {
            paramVO.setLastHandleEndTime(reqVo.getLastHandleEndTime().getTime());
        }
        paramVO.setLoginName(userName);
        log.info("查询流程中心参数为：{}", JSON.toJSONString(paramVO));
        QueryResult<ProcessVo> result = processApi.getProcesslist(paramVO);
        log.info("查询流程中心返回为：{}", JSON.toJSONString(result));
        List<SysProcessTemplate> templates = templateDao.selectAll();
        Map<String, SysProcessTemplate> templateMap = new HashMap<>();
        for (SysProcessTemplate template : templates) {
            templateMap.put(template.getTemplateId(), template);
        }

        List<ProcessInstance> list = new ArrayList<>();
        log.info(JSON.toJSONString(result.getContent()));
        for (ProcessVo process : result.getContent()) {
            SysProcessTemplate template = templateMap.get(process.getFdTemplateId());
            ProcessInstance instance = new ProcessInstance();
            instance.setProcessId(process.getFdId());
            instance.setSubject(process.getFdSubject());
            instance.setTemplateName(process.getFdTemplateName());
            instance.setLastHandlerTime(new Date(process.getFdLastHandlerTime()));
            instance.setCreator(process.getFdCreator());
            instance.setCurrentHandler(process.getFdCurrentHandler());
            instance.setCurrentNode(process.getFdCurrentNode());
            instance.setStatus(process.getFdStatus());
            instance.setEdit(false);
            if (template != null) {
                instance.setViewPath(template.getViewPath());
                instance.setEditPath(template.getEditPath());
                if (Objects.equals(template.getModificationNode(), process.getFdCurrentNode())) {
                    instance.setEdit(true);
                }
            }
            list.add(instance);
        }
        CommonListRspVo rsp = new CommonListRspVo(ResultCode.C_SUCCESS);
        rsp.setData(list);
        rsp.setTotal((int) result.getTotalSize());
        return rsp;
    }

    @Override
    public CommonRspVo getProcess(String processId, String userName) {
        SysProcessData processData = processDataDao.selectByPrimaryKey(processId);
        if (processData == null) {
            return new CommonRspVo(ResultCode.C_DATA_NOT_FOUND, "流程不存在");
        }

        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(processData.getFormData())) {
            Map<String, Object> dataForm = JSON.parseObject(processData.getFormData(), Map.class);
            map.put("formData", dataForm);
        }
        map.put("processId", processId);
        map.put("formId", processData.getFormId());

        ProcessFormVo processFormVo = new ProcessFormVo();
        processFormVo.setLoginName(userName);
        processFormVo.setProcessId(processId);
        ProcessInfo processInfo = processApi.getProcess(processFormVo);
        log.info("processInfo:<{}>", JSON.toJSONString(processInfo));
        List<String> handlerList = new ArrayList<>();
        List<String> handlerUserNameList = new ArrayList<>();

        if (processInfo.getCurrentHandlers().size() > 0) {
            for (HandlerInfo info : processInfo.getCurrentHandlers()) {
                handlerList.add(info.getFdName());
                handlerUserNameList.add(info.getFdLoginName());
            }
            String handlers = StringUtils.join(handlerList.toArray(), ",");
            String handlerUserNames = StringUtils.join(handlerUserNameList.toArray(), ",");
            map.put("currentHandler", handlers);
            map.put("currentHandlerUserName", handlerUserNames);
        }
        if (processInfo.getFdTaskInfo().getHandlerInfos() != null && processInfo.getFdTaskInfo().getHandlerInfos()
                .size() > 0) {
            map.put("currentHandlerNode", processInfo.getFdTaskInfo().getHandlerInfos().get(0).getNodeName());
        } else if (processInfo.getFdTaskInfo().getDrafterInfos() != null && processInfo.getFdTaskInfo().getDrafterInfos().size() > 0) {
            map.put("currentHandlerNode", processInfo.getFdTaskInfo().getDrafterInfos().get(0).getNodeName());
        } else if (processInfo.getFdTaskInfo().getAdminInfos() != null && processInfo.getFdTaskInfo().getAdminInfos().size() > 0) {
            map.put("currentHandlerNode", processInfo.getFdTaskInfo().getAdminInfos().get(0).getNodeName());
        } else {
            log.info("当前流程<{}>获取不到当前处理节点，流程状态<{}>", processId, processInfo.getFdProcessStatus());
        }
        List<SysProcessFile> fileList = processFileDao.selectByProcessId(processId);
        List<ProcessNodeInfo> nodeInfos = new ArrayList<>();
        for (SysProcessFile processFile : fileList) {
            ProcessNodeInfo nodeInfo = tran2ProcessNodeInfo(processFile);
            nodeInfos.add(nodeInfo);
        }
        map.put("nodeInfos", nodeInfos);
        map.put("status", processInfo.getFdProcessStatus());

        CommonRspVo rsp = new CommonRspVo(ResultCode.C_SUCCESS);
        rsp.setData(map);
        return rsp;
    }

    @Override
    public CommonRspVo getProcesTemplate(ProcessTemplate reqVo, int pageNo, int pageSize) {
        Pager<ProcessTemplateReqVo> pager = new Pager<>(pageNo, pageSize);
        List<String> templIds = null;
        if (!Strings.isEmpty(reqVo.getTemplateId())) {
            templIds = Arrays.asList(reqVo.getTemplateId().split(","));
        }
        pager.addParams("templIds", templIds);
        List<ProcessTemplateReqVo> processTemplates = sysProcessTemplateDao.selectProcesTemplates(pager);
        CommonListRspVo rsp = new CommonListRspVo();
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        rsp.setTotal(sysProcessTemplateDao.selectAll().size());
        rsp.setData(processTemplates);
        return rsp;
    }

    @Override
    public CommonRspVo updateSecuritiesStatus(String categoryId, String secuIntl,String status) {
        sysProcessTemplateDao.updateSecuritiesStatus(categoryId,secuIntl,status);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    private ProcessNodeInfo tran2ProcessNodeInfo(SysProcessFile processFile) {
        ProcessNodeInfo nodeInfo = new ProcessNodeInfo();
        nodeInfo.setNodeName(processFile.getHandlerNode());
        nodeInfo.setNodeHandlerName(processFile.getHandlerName());
        nodeInfo.setHandlerTime(processFile.getCreateTime());
        nodeInfo.setFileList(Lists.newArrayList());
        if (!StringUtils.isEmpty(processFile.getFileList())) {
            List<FileInfo> list = JSON.parseArray(processFile.getFileList(), FileInfo.class);
            nodeInfo.setFileList(list);
        }
        return nodeInfo;
    }
}
