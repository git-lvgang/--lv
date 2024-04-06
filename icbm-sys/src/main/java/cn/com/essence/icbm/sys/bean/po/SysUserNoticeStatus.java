package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sys_user_notice_status
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserNoticeStatus extends SysUserNoticeStatusKey implements Serializable {
    /**
     * 是否已读（1:已读；0:未读）
     */
    private String isRead;

    /**
     * 是否置顶（1:已置顶；0:未置顶）

     */
    private String isTop;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}