package cn.com.essence.icbm.sys.service.impl;
import java.util.*;

import cn.com.essence.icbm.sys.bean.po.SysCalendarEvent;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventCondReqVo;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventReqVo;
import cn.com.essence.icbm.sys.dao.SysCalendarEventDao;
import cn.com.essence.icbm.sys.service.CalendarEventService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: huangll
 * @date: 2021-1-13
 *
 * 日历事件服务类
 */
@Service
@Slf4j
public class CalendarEventServiceImpl implements CalendarEventService {

    @Autowired
    SysCalendarEventDao eventDao;

    @Override
    @Transactional
    public CommonRspVo add(CalenderEventReqVo req) {
        log.info("日历事件添加：日历信息{}", JSON.toJSONString(req));
        SysCalendarEvent event = new SysCalendarEvent();
        event.setFundId(req.getFundId());
        event.setEventDate(req.getEventDate());
        event.setEventTitle(req.getEventTitle());
        event.setEventDetail(req.getEventDetail());
        event.setEventType(req.getEventType());
        event.setEventName(req.getEventName());
        event.setCreateTime(new Date());
        int result = eventDao.insert(event);
        if (result == 0) {
            log.error("日历添加失败：result<{}>",result);
            return new CommonRspVo(ResultCode.C_FAIL);
        }
        log.info("日历事件添加成功：result<{}>",result);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @Override
    public List<SysCalendarEvent> getEvents(CalenderEventCondReqVo req) {
        Map<String, Object> params = new HashMap<>();
        List<String> eventNames = null;
        if (!Strings.isEmpty(req.getEventNames())) {
            // 不为空，按，切分
            eventNames = Arrays.asList(req.getEventNames().split(","));
        }
        params.put("fundId", req.getFundId());
        params.put("eventNames", eventNames);
        params.put("monthDate", req.getMonthDate());
        params.put("date", req.getDate());

        List<SysCalendarEvent> events = eventDao.selectEvents(params);
        return events;
    }
}
