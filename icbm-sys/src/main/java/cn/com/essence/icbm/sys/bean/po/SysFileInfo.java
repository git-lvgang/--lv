package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_file_info
 * @author 
 */
@Data
public class SysFileInfo implements Serializable {
    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 预览服务的文件ID
     */
    private String previewFileId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否已删除 （0：未删除，1：已删除）
     */
    private String isDelete;

    private static final long serialVersionUID = 1L;
}