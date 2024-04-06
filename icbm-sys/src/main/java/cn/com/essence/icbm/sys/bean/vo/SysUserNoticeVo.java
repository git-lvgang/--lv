package cn.com.essence.icbm.sys.bean.vo;

import cn.com.essence.icbm.sys.bean.po.SysNotice;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserNoticeVo extends SysNotice implements Serializable{

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
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date updateTime;
}
