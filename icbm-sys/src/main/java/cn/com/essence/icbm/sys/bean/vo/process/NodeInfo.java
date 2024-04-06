package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点信息
 *
 * @author huangll
 */
@Data
public class NodeInfo {

    /**
     * 节点id
     */
    private String id;

    /**
     * 节点名称
     */
    private String name;
    /**
     * 任务所在节点的类型
     */
    private String taskNodeType;
    /**
     * 节点的所有任务子节点信息，包括多级任务子节点
     */
    private List<String> taskIds = new ArrayList<>();

    /**
     * 流转方式，默认为0，0表示无流转方式
     */
    private String cooperateType = "0";

    /**
     * 父节点类型
     */
    private String parentNodeId;

    /**
     * 父节点类型
     */
    private String parentNodeType;

    /**
     * 表单key
     */
    private String formKey;
    /**
     * 节点表单pc视图
     */
    private String pcViewId;
    /**
     * 节点表单移动端视图
     */
    private String mobileViewId;
}
