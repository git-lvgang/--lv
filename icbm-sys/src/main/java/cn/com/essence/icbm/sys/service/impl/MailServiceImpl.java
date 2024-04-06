package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysMailInboxInfo;
import cn.com.essence.icbm.sys.dao.SysMailInboxInfoDao;
import cn.com.essence.icbm.sys.service.MailService;
import cn.com.essence.wefa.util.MailClient;
import cn.com.essence.wefa.util.MailInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author: huangll
 * @date: 2021-3-20
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private SysMailInboxInfoDao inboxDao;

    // axty@essence.com.cn
    @Value("${essence.ambs.basic.mail.username}")
    private String receiver;

    /** 间隔3小时 = 3 * 60 * 60 * 1000 */
    private static final long INTERVAL_3_HOURS = 10800000L;

//    @Scheduled(cron = "0 */10 * * * *")
//    @SchedulerLock(name = "mailTask", lockAtLeastFor = "9m", lockAtMostFor = "9m")
//    public void receiverMailTask() {
//        getMails(null);
//    }

    /**
     * 凌晨00:10:00重新扫描一次前一天的所有邮件，避免遗漏
     */
//    @Scheduled(cron = "0 10 0 * * ?")
//    @SchedulerLock(name = "yesterdayReceiverMailTask", lockAtLeastFor = "40m", lockAtMostFor = "40m")
//    public void yesterdayReceiverMailTask() {
//        log.info("开始全量拉取昨天的所有邮件");
//        getMails(yesterday());
//    }





    public  void getMails(Date date) {
        if (date == null) {
            // 不传时间默认取邮件最大接收时间，并回拔5分钟
            Date maxdate = inboxDao.selectMaxReceiverTime(receiver);
            if (maxdate == null) {
                // 如果表里面没有数据，就取最新时间
                maxdate = new Date();
            }
            // 时间往回拨3个时间，避免大批量邮件被遗漏
            date = new Date(maxdate.getTime() - INTERVAL_3_HOURS);
        }
        log.info("定时器准备拉取邮件，当前时间：{}, 邮件拉取时间：{}",
                DateUtil.format(new Date(), DatePattern.NORM_DATETIME_FORMAT),
                DateUtil.format(date, DatePattern.NORM_DATETIME_FORMAT));

        // TODO 接配置,确定收件箱的白名单
        List<String> senderList = new ArrayList<>();

        List<String> uidList = inboxDao.selectMailUids(date, receiver, senderList);

        try {
            List<MailInfo> mailInfoList = mailClient.acquireEmails(date, new HashSet<>(senderList) ,new HashSet<>(uidList));
            log.info("拉取时间：{}，拉取邮件信息为：{}", DateUtil.format(date, DatePattern.NORM_DATETIME_FORMAT), JSON.toJSONString(mailInfoList));
            for (MailInfo mailInfo : mailInfoList) {
                // 将读到的邮件内容,写入表中
                SysMailInboxInfo info = new SysMailInboxInfo();
                info.setMailUid(mailInfo.getMailUID());
                // 一个邮件可能会多个收件人，但是这里只要自己就好
                info.setMailReceiver(mailClient.getUsername());
                info.setMailSender(mailInfo.getSender());
                info.setMailSubject(mailInfo.getSubject());
                info.setMailContent(mailInfo.getContent());
                info.setMailHtmlContent(mailInfo.getHtmlContent());
                info.setReceiverTime(mailInfo.getReceiverTime());
                info.setSentTime(mailInfo.getSentTime());
                if (!CollectionUtils.isEmpty(mailInfo.getFileList())) {
                    info.setAttachment(JSON.toJSONString(mailInfo.getFileList()));
                }
                info.setCreateTime(new Date());
                try {
                    String mailHtmlContent = info.getMailHtmlContent();
                    if (StringUtils.isNotBlank(mailHtmlContent)) {
                        // 空格压缩
                        String content = mailHtmlContent.replaceAll("\\s+", " ");
                        info.setMailHtmlContent(content);
                    }
                    inboxDao.insert(info);
                } catch (DuplicateKeyException e) {
                    // 利用表的唯一做出去重, 理论应该是很少，因为已经有了处理集合
                    log.info("邮件已处理，邮件主题<{}> 邮件发件人<{}> 邮件唯一标识<{}>", info.getMailSubject(), info.getMailSender(), info.getMailUid());
                } catch (Exception e) {
                    log.error("邮件处理异常,详细:" + info.toString() + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.info("邮件拉取失败！", e);
        }
    }

    private Date yesterday() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        LocalDateTime yesterday = now.minusDays(1);
        Calendar calendar = Calendar.getInstance();
        int month = yesterday.getMonthValue() - 1;
        calendar.set(yesterday.getYear(), month, yesterday.getDayOfMonth(), 0, 0 , 0);
        return calendar.getTime();
    }

}
