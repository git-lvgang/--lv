package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NoticeReqVo implements Serializable {

    /**
     * 通告类型（1:运营服务类；2:监管法规类；
     * 3:公司发文；4:内部通知；5:其它）"
     */
    private String noticeType;

    /**
     * 重要程度
     */
    private String importance;

    /**
     * 通告标题
     */
    private String noticeTitle;

    /**
     * 通告内容
     */
    private String noticeContent;

    /**
     * 通告摘要
     */
    private String noticeSummary;

    /**
     * 通告发送人ID
     */
    private Integer noticeSenderId;


}
