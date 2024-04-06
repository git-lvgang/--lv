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
@ApiModel("客户常用菜单设置")
public class SysFrequentlyFunctionSet extends CommonReqVo {

    private Integer id;

    /**
     * 客户代码id
     */
    @ApiModelProperty("客户代码")
    private String custCode;

    /**
     *菜单id
     */
    @ApiModelProperty("菜单id")
    private String menuId;

    /**
     *菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 菜单排序序号
     */
    @ApiModelProperty("菜单排序序号")
    private String menuRankNo;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    private static final long serialVersionUID = 1L;

}