package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysCalendarEvent;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventCondReqVo;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventReqVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;

import java.util.List;

public interface CalendarEventService {

    /**
     * 增加日历事件
     * @param req
     * @return
     */
    CommonRspVo add(CalenderEventReqVo req);

    /**
     * 查询
     * @param req
     * @return
     */
    List<SysCalendarEvent> getEvents(CalenderEventCondReqVo req);
}
