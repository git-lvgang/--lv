package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Lxy
 */
@Data
public class SysParamItemsVo implements Serializable {

    /**
     * 参数id
     */
    private Integer paramItemId;

    /**
     * 参数代码
     */
    private String paramCode;

    /**
     *参数值
     */
    private char paramItem;

    /**
     *参数名称
     */
    private String paramItemName;

}
