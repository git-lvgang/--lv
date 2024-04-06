package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huangll
 * Date: 2021-1-26
 * Description:
 */
@Data
public class SearchBaseInfo<T> implements Serializable {
    /**
     * 菜单路径
     */
    private String path;

    /**
     * 明细列表
     */
    private List<T> list;
}
