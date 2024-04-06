package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

import java.util.List;


/**
 * 流程相关信息
 *
 * @author huangll
 */
@Data
public class ProcessInfo {

    /**
     * 流程实例ID
     */
    private String fdProcessId;

    private String fdProcessStatus;

    private List<HandlerInfo> currentHandlers;

    private TaskInfo fdTaskInfo;

}
