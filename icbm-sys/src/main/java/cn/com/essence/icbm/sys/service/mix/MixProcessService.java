package cn.com.essence.icbm.sys.service.mix;

import cn.com.essence.wefa.core.bean.CommonRspVo;

public interface MixProcessService {
    /**
     * 工作台待办事项查询
     * @param userName
     * @return
     */
    CommonRspVo getToDo(String userName);
}
