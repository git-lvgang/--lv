package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: huangll
 * @date: 2021-3-10
 */
@Data
public class ProcessTemplateReqVo implements Serializable {
    /**
     * 流程模板ID
     */
    private String templateId;

    /**
     * 流程模板名称
     */
    private String templateName;

    /**
     *  流程说明
     */
    private String describe;

    /**
     * 查看路径
     */
    private String viewPath;

    /**
     * 编辑路径
     */
    private String editPath;

    /**
     * 备注
     */
    private String remark;

    /**
     * 表单可编辑节点
     */
    private String modificationNode;

    /**
     * 是否启用
     */
    private String status;
}
