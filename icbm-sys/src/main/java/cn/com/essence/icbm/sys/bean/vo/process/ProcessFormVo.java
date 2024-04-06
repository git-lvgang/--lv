package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

/**
 * 流程查询入参
 * @author huangll
 */
@Data
public class ProcessFormVo {

    /**
     * 流程实例ID
     */
    private String processId;

    /**
     * 表单实例ID
     */
    private String formInstanceId;

    /**
     * 登录名
     */
    private String loginName;
}
