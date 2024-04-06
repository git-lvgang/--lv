package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: huangll
 * @date: 2021-3-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessTemplate implements Serializable {
    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;
}
