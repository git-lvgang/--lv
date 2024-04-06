package cn.com.essence.icbm.sys.bean.vo.process.mix;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProcessInstanceMix implements Serializable {
    /**
     * 流程ID
     */
    private String processId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 最后处理时间
     */
    private Date lastHandlerTime;

    /**
     * 当前节点
     */
    private String currentNode;

    /**
     * 当前处理人
     */
    private String currentHandler;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 查看路径
     */
    private String viewPath;

    /**
     * 编辑路径
     */
    private String editPath;

    /**
     * 是否可编辑
     */
    private boolean edit;

    /**
     *
     * 流程模板ID
     */
    private String fdTemplateId;
}
