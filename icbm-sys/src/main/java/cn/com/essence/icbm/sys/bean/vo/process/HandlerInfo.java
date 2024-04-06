package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

/**
 * 处理人信息
 * @author huangll
 */
@Data
public class HandlerInfo {

    /**
     * 处理人id
     */
    private String fdId;

    private String id;

    /**
     * 处理人名称
     */
    private String fdName;

    private String fdLoginName;

    /**
     * 部门名称
     */
    private String fdParentName;

    /**
     * 头像地址
     */
    private String fdImg;

    private String avatar;

    /**
     * 操作事件
     */
    private Long operationTime;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 对应的节点信息
     */
    private NodeInfo nodeInfo;

    /**
     * 所属节点信息
     */
    private NodeInfo fromNode;

}
