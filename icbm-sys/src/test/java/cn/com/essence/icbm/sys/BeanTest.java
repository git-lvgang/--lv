package cn.com.essence.icbm.sys;

import cn.com.essence.icbm.sys.bean.po.*;
import cn.com.essence.icbm.sys.bean.vo.*;
import cn.com.essence.icbm.sys.util.BeanUtil;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-2-4
 * Description:
 */
public class BeanTest {
    @Test
    public void testBean() {
        PlcFundBase base = new PlcFundBase();
        BeanUtil.coverGetMethod(base);

        SysCalendarEvent event = new SysCalendarEvent();
        BeanUtil.coverGetMethod(event);

        SysFrequentlyFunctionSet frequentlyFunctionSet = new SysFrequentlyFunctionSet();
        BeanUtil.coverGetMethod(frequentlyFunctionSet);

        SysMessage message = new SysMessage();
        BeanUtil.coverGetMethod(message);

        SysNotice notice = new SysNotice();
        BeanUtil.coverGetMethod(notice);

        SysParam param = new SysParam();
        BeanUtil.coverGetMethod(param);

        SysParamItems sysParamItems = new SysParamItems();
        BeanUtil.coverGetMethod(sysParamItems);

        SysSearchIdx sysSearchIdx = new SysSearchIdx();
        BeanUtil.coverGetMethod(sysSearchIdx);

        SysUserNoticeStatus sysUserNoticeStatus = new SysUserNoticeStatus();
        BeanUtil.coverGetMethod(sysUserNoticeStatus);

        SysUserNoticeStatusKey key = new SysUserNoticeStatusKey();
        BeanUtil.coverGetMethod(key);
    }

    @Test
    public void testBeanVo() {
        CalenderEventCondReqVo calenderEventCondReqVo = new CalenderEventCondReqVo();
        BeanUtil.coverGetMethod(calenderEventCondReqVo);

        CalenderEventReqVo calenderEventReqVo = new CalenderEventReqVo();
        BeanUtil.coverGetMethod(calenderEventReqVo);

        FundInfo fundInfo = new FundInfo();
        BeanUtil.coverGetMethod(fundInfo);

        MessageListReqVo messageListReqVo = new MessageListReqVo();
        BeanUtil.coverGetMethod(messageListReqVo);

        NoticeListReqVo noticeListReqVo = new NoticeListReqVo();
        BeanUtil.coverGetMethod(noticeListReqVo);

        NoticeListReqVo noticeReqVo = new NoticeListReqVo();
        BeanUtil.coverGetMethod(noticeReqVo);

        SearchBaseInfo<FundInfo> searchBaseInfo = new SearchBaseInfo<>();
        BeanUtil.coverGetMethod(searchBaseInfo);

        SearchInfo searchInfo = new SearchInfo();
        BeanUtil.coverGetMethod(searchInfo);

        SearchMenuInfo menuInfo = new SearchMenuInfo();
        BeanUtil.coverGetMethod(menuInfo);

        SsoValidateVo ssoValidateVo = new SsoValidateVo();
        BeanUtil.coverGetMethod(ssoValidateVo);

        SysFrequentlyFunctionSetVo sysFrequentlyFunctionSetVo = new SysFrequentlyFunctionSetVo();
        BeanUtil.coverGetMethod(sysFrequentlyFunctionSetVo);

        SysParamItemsVo sysParamItemsVo = new SysParamItemsVo();
        BeanUtil.coverGetMethod(sysParamItemsVo);

        SysParamVo sysParamVo = new SysParamVo();
        BeanUtil.coverGetMethod(sysParamVo);

        SysUserNoticeVo sysUserNoticeVo = new SysUserNoticeVo();
        BeanUtil.coverGetMethod(sysUserNoticeVo);

        UserValidateReqVo userValidateReqVo = new UserValidateReqVo();
        BeanUtil.coverGetMethod(userValidateReqVo);

    }
}
