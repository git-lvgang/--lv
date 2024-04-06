package cn.com.essence.icbm.sys.bean.vo;

import cn.com.essence.wefa.core.bean.CommonListReqVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MessageListReqVo extends CommonListReqVo {

    /**
     * 主题
     */
    private String subject;

    /**
     * 重要程度
     */
    private String importance;

    /**
     * 是否已读
     */
    private String isRead;

    /**
     *  开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
}
