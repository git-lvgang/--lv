package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 流程实例VO
 *
 * @author huangll
 */
@Data
@Accessors(chain = true)
public class ProcessVo {

    /**
     * 流程实例ID
     */
    private String fdId;

    /**
     * 流程标题
     */
    private String fdSubject;

    /**
     * 流程状态
     */
    private String fdStatus;

    /**
     * 流程创建者
     */
    private String fdCreator;

    /**
     * 流程创建时间
     */
    private Long fdCreateTime;

    /**
     * 流程结束时间
     */
    private Long fdEndTime;

    /**
     * 最后更新时间
     */
    private Long fdLastHandlerTime;

    /**
     * 当前节点Id
     */
    private String fdCurrentNodeId;
    /**
     * 当前节点名称
     */
    private String fdCurrentNode;

    /**
     *  当前处理人Id
     */
    private String fdCurrentHandlerId;
    /**
     *  当前处理人名称
     */
    private String fdCurrentHandler;

    /**
     * 流程异常时间
     */
    private Long fdErrorTime;

    /**
     * 表单URL
     */
    private String fdFormUrl;

    /**
     * 业务表单实例ID
     */
    private String fdFormInstanceId;

    /**
     * 业务表单标识
     */
    private String fdFormKey;
    /**
     * 流程定义ID
     */
    private String fdDefinitionId;

    /**
     * 流程模板ID
     */
    private String fdTemplateId;

    /**
     * 流程模板名称
     */
    private String fdTemplateName;

    /**
     * 流程创建人详细信息
     */
    //private HandlerInfo fdCreatorInfo;

    /**
     * 父流程实例启动当前流程的节点名称
     */
    private String fdNodeNameOnParent;

    /**
     * 流程是否存在跟踪
     */
    private Boolean fdIsFollow;
}
