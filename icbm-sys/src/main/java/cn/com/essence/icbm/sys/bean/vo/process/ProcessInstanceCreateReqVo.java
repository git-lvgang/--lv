package cn.com.essence.icbm.sys.bean.vo.process;

import cn.com.essence.icbm.sys.bean.vo.FileInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: huangll
 * @date: 2021-3-10
 */
@Data
public class ProcessInstanceCreateReqVo implements Serializable {
    /**
     * 流程名称
     */
    private String subject;

    /**
     * 表单数据
     */
    private Map<String, Object> formData;

    /**
     * 文件列表
     */
    private List<FileInfo> fileList;

}
