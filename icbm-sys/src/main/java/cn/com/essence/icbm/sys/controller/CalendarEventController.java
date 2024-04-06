package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.po.SysCalendarEvent;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventCondReqVo;
import cn.com.essence.icbm.sys.bean.vo.CalenderEventReqVo;
import cn.com.essence.icbm.sys.service.CalendarEventService;
import cn.com.essence.wefa.component.log.SysLog;
import cn.com.essence.wefa.core.bean.CommonListRspVo;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class CalendarEventController {

    @Autowired
    private CalendarEventService service;

    private static final String DATE_FORMAT = "YYYYMM";

    // TODO 创建事件
    @PostMapping("/calendar/event")
    @RequiresUser
    @SysLog(moduleName = "[Icbm_Sys Component 日历事件]",operation = "新增事件")
    public CommonRspVo addEvent(CalenderEventReqVo req) {
        service.add(req);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }

    @GetMapping("/calendar/event/list")
    @RequiresUser
    public CommonRspVo getEvents(CalenderEventCondReqVo req) {
        // TODO 产品权限相关
        CommonListRspVo rsp = new CommonListRspVo();
        if (Strings.isEmpty(req.getMonthDate()) && Strings.isEmpty(req.getDate())) {
            // 如果这两个都为空，则默认查当月了，防止查全部的
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String curMonthDate = sdf.format(new Date());
            req.setMonthDate(curMonthDate);
            log.debug("curMonthDate:<{}>", curMonthDate);
        }

        List<SysCalendarEvent> events = service.getEvents(req);
        rsp.setTotal(events.size());
        rsp.setData(events);
        rsp.setRtnCode(ResultCode.C_SUCCESS);

        return rsp;
    }
}
