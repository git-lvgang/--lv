package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.service.MailService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huangll
 * @date: 2021-3-20
 */
@RestController
@Slf4j
public class MailController {
    @Autowired
    MailService service;

    @PostMapping("/mail/trigger")
    public CommonRspVo getMails(@RequestBody String startDate) {
        DateTime date = DateUtil.parse(startDate, "yyyy-MM-dd HH:mm:ss");
        service.getMails(date);
        return new CommonRspVo(ResultCode.C_SUCCESS);
    }
}
