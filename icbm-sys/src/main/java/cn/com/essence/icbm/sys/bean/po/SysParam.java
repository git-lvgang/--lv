package cn.com.essence.icbm.sys.bean.po;

import cn.com.essence.wefa.core.bean.CommonReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Lxy
 * sys_param
 * @author 
 */
@Data
@ApiModel("系统公参")
public class SysParam  extends CommonReqVo {
    /**
     * 参数代码
     */
    @ApiModelProperty("参数代码")
    private String paramCode;

    /**
     * 参数名称
     */
    @ApiModelProperty("参数名称")
    private String paramName;

    /**
     *维护标识(0-可维护,1-不可删除,2-不可修改,3-不可维护)
     */
    @ApiModelProperty("维护标识(0-可维护,1-不可删除,2-不可修改,3-不可维护)")
    private char maintainFlag;

    /**
     *备注
     */
    @ApiModelProperty("备注")
    private String remark;
}