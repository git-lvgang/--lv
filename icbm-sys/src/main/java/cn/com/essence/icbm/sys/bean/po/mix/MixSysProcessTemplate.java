package cn.com.essence.icbm.sys.bean.po.mix;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MixSysProcessTemplate implements Serializable {
    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 查看路径
     */
    private String viewPath;

    /**
     * 编辑路径
     */
    private String editPath;

    /**
     * 流程说明
     */
    private String describe;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 可更新节点
     */
    private String modificationNode;

    /**
     * 状态(1:启用;0停用)
     */
    private String status;

    private static final long serialVersionUID = 1L;
}
