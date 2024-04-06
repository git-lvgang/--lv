package cn.com.essence.icbm.sys.service.mix.impl;

import cn.com.essence.icbm.sys.bean.po.mix.MixSysProcessTemplate;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessListParamVo;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessVo;
import cn.com.essence.icbm.sys.bean.vo.process.QueryResult;
import cn.com.essence.icbm.sys.bean.vo.process.mix.ProcessInstanceMix;
import cn.com.essence.icbm.sys.dao.mix.MixSysProcessTemplateDao;
import cn.com.essence.icbm.sys.remote.ProcessApi;
import cn.com.essence.icbm.sys.service.mix.MixProcessService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Slf4j
public class MixProcessServiceImpl implements MixProcessService {

    @Autowired
    private MixSysProcessTemplateDao templateDaoMix;

    @Autowired
    private ProcessApi processApi;

    @Value("${essence.ambs.basic.process-label}")
    private String labelName;
    
    @Override
    public CommonRspVo getToDo(String userName) {
        ProcessListParamVo paramVO = new ProcessListParamVo();
        paramVO.setOffset(0);
        paramVO.setPageSize(300);
        paramVO.setLabelName(labelName);
        paramVO.setMydoc("myApproving");
        paramVO.setLoginName(userName);
        List<ProcessInstanceMix> list= new ArrayList<>();
        int a=1;
        while(true){
            QueryResult<ProcessVo> result = processApi.getProcesslist(paramVO);
            log.info("查询流程中心参数为：{}", JSON.toJSONString(paramVO));
            List<MixSysProcessTemplate> templates = templateDaoMix.selectAll();
            log.info("查询流程中心返回为：{}",JSON.toJSONString(result));
            List<ProcessInstanceMix> processInstances=getProcessInstances(result,templates);
            paramVO.setOffset(paramVO.getPageSize()*a);
            paramVO.setPageSize(300);
            a++;
            list.addAll(processInstances);
            if(processInstances.size()<=0||processInstances.size()<paramVO.getPageSize()){
                break;
            }
        }
        int ruChi=0;
        int chuChi=0;
        int baoGao=0;
        List<MixSysProcessTemplate> templates = templateDaoMix.selectAll();
        for(ProcessInstanceMix processInstance:list){
            for(MixSysProcessTemplate sysProcessTemplate:templates){
                if(processInstance.getFdTemplateId().equals(sysProcessTemplate.getTemplateId())&&sysProcessTemplate.getTemplateName().indexOf("出池")!=-1){
                    ++chuChi;
                }else if(processInstance.getFdTemplateId().equals(sysProcessTemplate.getTemplateId())&&sysProcessTemplate.getTemplateName().indexOf("入池")!=-1){
                    ++ruChi;
                }else if(processInstance.getFdTemplateId().equals(sysProcessTemplate.getTemplateId())&&sysProcessTemplate.getTemplateName().equals("研究报告上传")){
                    ++baoGao;
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outPool",chuChi);
        jsonObject.put("internal",baoGao);
        jsonObject.put("inPool",ruChi);
        jsonObject.put("todoList",ruChi+chuChi+baoGao);
        CommonRspVo rsp = new CommonRspVo();
        rsp.setData(jsonObject);
        rsp.setRtnCode(ResultCode.C_SUCCESS);
        return rsp;
    }

    private  List<ProcessInstanceMix> getProcessInstances(QueryResult<ProcessVo> result, List<MixSysProcessTemplate> templates){
        Map<String, MixSysProcessTemplate> templateMap = new HashMap<>();
        for (MixSysProcessTemplate template : templates) {
            templateMap.put(template.getTemplateId(), template);
        }
        List<ProcessInstanceMix> list = new ArrayList<>();
        for (ProcessVo process : result.getContent()) {
            MixSysProcessTemplate template = templateMap.get(process.getFdTemplateId());
            ProcessInstanceMix instance = new ProcessInstanceMix();
            instance.setProcessId(process.getFdId());
            instance.setFdTemplateId(process.getFdTemplateId());
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
        return list;
    }
}
