package cn.com.essence.icbm.sys.bean.vo.process;

import cn.com.essence.icbm.sys.bean.vo.FileInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Data
public class ProcessNodeInfo implements Serializable {
    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 处理人名称
     */
    private String nodeHandlerName;

    /**
     * 处理时间
     */
    private Date handlerTime;

    /**
     * 文件列表
     */
    private List<FileInfo> fileList;
}
