package cn.com.essence.icbm.sys.bean.po;

import cn.com.essence.wefa.core.bean.CommonReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Lxy
 * sys_param_Items
 * @author 
 */
@Data
@ApiModel("系统公参子项")
public class SysParamItems  extends CommonReqVo {
    /**
     * 参数id
     */
    @ApiModelProperty("参数id")
    private Integer paramItemId;

    /**
     * 参数代码
     */
    @ApiModelProperty("参数代码")
    private String paramCode;

    /**
     *参数值
     */
    @ApiModelProperty("参数值")
    private char paramItem;

    /**
     *参数名称
     */
    @ApiModelProperty("参数名称")
    private String paramItemName;
}