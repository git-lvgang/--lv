package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 流程定义VO
 *
 * @author huangll
 */
@Data
@Accessors(chain = true)
public class ProcessListParamVo {

    /**
     * 从第?条开始查询，起始值为0
     */
    private int offset = 0;

    private int pageSize = 20;

    /**
     * 流程状态
     */
    private String status;
    /**
     * 查询数据模型 myAll,myCreated,myApproving,myApproved,myMonitor
     */
    private String mydoc;
    /**
     * 标题
     */
    private String fdSubject;
    /**
     * 创建人
     */
    private String fdCreator;

    /**
     * 最后处理时间:起始
     */
    private Long lastHandleBeginTime;
    /**
     * 最后处理时间:截止
     */
    private Long lastHandleEndTime;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 模板id，多个用,隔开
     */
    private String templateIds;
}
