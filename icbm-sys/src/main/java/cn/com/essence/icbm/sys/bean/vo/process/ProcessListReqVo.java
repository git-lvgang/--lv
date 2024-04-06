package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: huangll
 * @date: 2021-3-11
 */
@Data
public class ProcessListReqVo {
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
    private String subject;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 模板id，多个id用，隔开
     */
    private String templateIds;

    /**
     * 最后处理时间:起始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastHandleBeginTime;
    /**
     * 最后处理时间:截止
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastHandleEndTime;
}
