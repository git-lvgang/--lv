package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_user_notice_status
 * @author 
 */
@Data
public class SysUserNoticeStatusKey implements Serializable {
    /**
     * 通告ID
     */
    private Integer noticeId;

    /**
     * 用户ID
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;
}