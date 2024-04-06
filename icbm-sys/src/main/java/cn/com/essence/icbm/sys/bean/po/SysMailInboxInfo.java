package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_mail_inbox_info
 * @author 
 */
@Data
public class SysMailInboxInfo implements Serializable {
    /**
     * 邮件ID
     */
    private Integer id;

    /**
     * 邮件UID(每个收件箱应该是唯一)
     */
    private String mailUid;

    /**
     * 收件人
     */
    private String mailReceiver;

    /**
     * 发送人
     */
    private String mailSender;

    /**
     * 主题
     */
    private String mailSubject;

    /**
     * 邮件内容
     */
    private String mailContent;

    private String mailHtmlContent;

    /**
     * 收件时间
     */
    private Date receiverTime;

    /**
     * 邮件发送时间
     */
    private Date sentTime;

    /**
     * 附件列表
     */
    private Object attachment;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}