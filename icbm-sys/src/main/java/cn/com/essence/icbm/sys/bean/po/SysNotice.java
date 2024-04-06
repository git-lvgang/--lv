package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * sys_notice
 * @author 
 */
@Data
public class SysNotice implements Serializable {
    /**
     * 通知ID
     */
    private Integer noticeId;

    /**
     * 通告类型（1:运营服务类；2:监管法规类；
3:公司发文；4:内部通知；5:其它）
     */
    private String noticeType;

    /**
     * 重要程度（1:紧急；2:重要；3:普通）
     */
    private String importance;

    /**
     * 通告标题
     */
    private String noticeTitle;

    /**
     * 通告详细内容
     */
    private String noticeContent;

    /**
     * 通告内容摘要
     */
    private String noticeSummary;

    /**
     * 通告附件
     */
    private String noticeAppendix;

    /**
     * 是否已删除 （0：未删除，1：已删除）

     */
    private String isDeleted;

    /**
     * 通告发送人ID
     */
    private Integer noticeSenderId;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishedTime;

    private static final long serialVersionUID = 1L;
}