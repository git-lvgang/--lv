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
public class ProcessOperationReqVo implements Serializable {
    /**
     * 当前处理节点
     */
    private String currentHandlerNode;

    /**
     * 操作参数
     */
    private OperationReqVo parameters;

    /**
     * 表单数据
     */
    private Map<String, Object> formData;

    /**
     * 文件列表
     */
    private List<FileInfo> fileList;

}
