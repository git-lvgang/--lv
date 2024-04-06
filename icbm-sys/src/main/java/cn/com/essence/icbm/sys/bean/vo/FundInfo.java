package cn.com.essence.icbm.sys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundInfo implements Serializable {

    /**
     * 产品ID
     */
    private Integer fundId;

    /**
     * 产品代码
     * 实际对应的是销售代码
     */
    private String fundCode;

    /**
     * 产品全称
     */
    private String fundName;
}
