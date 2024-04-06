package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: huangll
 * @date: 2021-3-9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateParam implements Serializable {
    /**
     * 偏移
     */
    private int offset;
    /**
     * 条数
     */
    private int pageSize;
    /**
     * 标签名称
     */
    private String labelName;
}
