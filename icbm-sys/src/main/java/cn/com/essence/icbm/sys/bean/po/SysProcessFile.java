package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_process_file
 * @author 
 */
@Data
public class SysProcessFile implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private String processId;

    /**
     * 处理节点
     */
    private String handlerNode;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理人ID
     */
    private Integer handlerId;

    private Date createTime;

    /**
     * 文件列表
     */
    private String fileList;

    /**
     * 处理人名称
     */
    private String handlerName;

    private static final long serialVersionUID = 1L;
}