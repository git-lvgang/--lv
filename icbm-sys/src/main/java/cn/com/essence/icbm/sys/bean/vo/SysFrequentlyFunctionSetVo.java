package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Lxy
 */
@Data
public class SysFrequentlyFunctionSetVo implements Serializable {

    /**
     *id
     */
    private Integer id;

    /**
     * 客户代码
     */
    private String custCode;

    /**
     *菜单id
     */
    private String menuId;

    /**
     *菜单名称
     */
    private String menuName;

    /**
     * 菜单排序序号
     */
    private String menuRankNo;

    /**
     * 地址
     */
    private String address;
}
