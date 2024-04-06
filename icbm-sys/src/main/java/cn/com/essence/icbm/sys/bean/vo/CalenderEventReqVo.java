package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalenderEventReqVo implements Serializable {
    /**
     * 产品ID
     */
    private Integer fundId;

    /**
     * 事件标题
     */
    private String eventTitle;

    /**
     * 事件详情
     */
    private String eventDetail;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件类别
     */
    private String eventType;

    /**
     * 事件日期YYYYMMDD
     */
    private String eventDate;

}
