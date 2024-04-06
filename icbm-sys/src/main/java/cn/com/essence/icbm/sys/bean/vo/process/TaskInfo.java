package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-4-10
 */
@Data
public class TaskInfo {
    /***
     *
     */
    List<DrafterInfo> drafterInfos;

    /**
     *
     */
    List<HandlerInfoVo> handlerInfos;

    /**
     *
     */
    List<HandlerInfoVo> adminInfos;
}
