package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 流程创建VO
 * @author huangll
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessCreateVo implements Serializable {
    /**流程模板ID(模板ID和定义ID至少一个不为空)*/
    private String templateId;

    /** 流程定义ID */
    private String definitionId;

    /** 来源系统（可为空） */
    private String systemCode;

    /** 业务模块标识（可为空）*/
    private String moduleCode;

    /** 业务表单模板标识（可为空）*/
    private String formTemplateCode;
    /**
     * 表单实例ID
     */
    private String formInstanceId;
    /**
     * 表单实例Model Name
     */
    private String formInstanceModel;

    /**
     * 登录名
     */
    private String loginName;
}
