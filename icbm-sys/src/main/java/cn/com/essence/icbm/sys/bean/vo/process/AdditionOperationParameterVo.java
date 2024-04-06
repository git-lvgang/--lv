package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

/**
 * 附加操作信息
 *
 * @author huangll
 */
@Data
public class AdditionOperationParameterVo {

    /** 操作类型 */
    private String operationType;

    /** 操作身份 */
    private String operationIdentity;

    /** 操作参数 */
    private String parameter;
}
