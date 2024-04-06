package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */

@Data
public class SearchInfo implements Serializable {

    /**
     * 菜单
     */
    private List<SearchMenuInfo> menus;

    /**
     * 产品
     */
    private SearchBaseInfo<FundInfo> fund;
}
