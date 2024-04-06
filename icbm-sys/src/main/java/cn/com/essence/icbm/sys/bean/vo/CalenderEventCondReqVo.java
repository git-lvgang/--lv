package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CalenderEventCondReqVo implements Serializable {

    /**
     * 产品ID
     */
    private String fundId;

    /**
     * 事件名称,如果有多个用,隔开
     */
    private String eventNames;

    /**
     * 日期
     */
    private String date;

    /**
     * 日期(到月)
     */
    private String monthDate;
}
