package cn.com.essence.icbm.sys.bean.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_search_idx
 * @author 
 */
@Data
public class SysSearchIdx implements Serializable {
    /**
     * 自增ID-主键
     */
    private Integer id;

    /**
     * 索引全称
     */
    private String indexName;

    /**
     * 索引拼音首字母
     */
    private String indexPingyin;

    /**
     * 索引对应键值
     */
    private String indexKey;

    /**
     * 索引类型(001-菜单，002-产品全称，003-产品销售代码，004-机构)
     */
    private String indexType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新
     */
    private Date updateTime;

    /**
     * 是否已删除 （0：未删除，1：已删除）
     */
    private String isDeleted;

    private String indexDetail;

    private static final long serialVersionUID = 1L;
}