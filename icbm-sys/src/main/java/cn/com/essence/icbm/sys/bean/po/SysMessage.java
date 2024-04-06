package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * sys_message
 * @author 
 */
@Data
public class SysMessage implements Serializable {
    /**
     * 消息ID
     */
    private Integer messageId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 重要程度（1:紧急；2:重要；3:普通）
     */
    private String importance;

    /**
     * 消息主题
     */
    private String messageSubject;

    /**
     * 消息详细内容
     */
    private String messageContent;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否已删除 （0：未删除，1：已删除）

     */
    private String isDeleted;

    /**
     * 是否已读（0:未读；1:已读）

     */
    private String isRead;

    /**
     * 是否置顶（0:未置顶；1:已置顶）

     */
    private String isTop;

    /**
     * 最后更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}