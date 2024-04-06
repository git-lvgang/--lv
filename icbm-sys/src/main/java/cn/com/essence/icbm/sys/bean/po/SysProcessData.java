package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_process_data
 * @author 
 */
@Data
public class SysProcessData implements Serializable {
    /**
     * 流程ID
     */
    private String processId;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 表单数据(json)
     */
    private String formData;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 创建者ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流程标题
     */
    private String subject;

    private static final long serialVersionUID = 1L;
}