package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_calendar_event
 * @author 
 */
@Data
public class SysCalendarEvent implements Serializable {
    /**
     * 事件ID

     */
    private Integer eventId;

    /**
     * 产品ID
     */
    private Integer fundId;

    /**
     * 事件日期YYYYMMDD
     */
    private String eventDate;

    /**
     * 事件标题
     */
    private String eventTitle;

    /**
     * 事件详情
     */
    private String eventDetail;

    /**
     * 事件类别(1:产品事件，2:运营事件)
     */
    private String eventType;

    /**
     * 事件名称编码(对应关系查看字典表)
     */
    private String eventName;

    /**
     * 创建时间

     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}