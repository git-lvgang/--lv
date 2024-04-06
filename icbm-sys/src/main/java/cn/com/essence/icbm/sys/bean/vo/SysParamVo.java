package cn.com.essence.icbm.sys.bean.vo;

import cn.com.essence.wefa.core.bean.CommonListReqVo;
import lombok.Data;

/**
 * Lxy
 */
@Data
public class SysParamVo extends CommonListReqVo {

    /**
     * 参数代码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     *维护标识(0-可维护,1-不可删除,2-不可修改,3-不可维护)
     */
    private char maintainFlag;

    /**
     *备注
     */
    private String remark;
}
