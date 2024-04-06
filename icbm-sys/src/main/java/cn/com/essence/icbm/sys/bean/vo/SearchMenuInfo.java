package cn.com.essence.icbm.sys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: huangll
 * Date: 2021-1-26
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchMenuInfo implements Serializable {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String path;
}
