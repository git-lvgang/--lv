package cn.com.essence.icbm.sys.scheduler;

import cn.com.essence.icbm.sys.service.IndexService;
import cn.com.essence.wefa.component.bean.Menu;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MenuIdxScheduler {

    @Autowired
    private IndexService<Menu> indexService;

    @Scheduled(cron = "0 */60 * * * *")
    @SchedulerLock(name = "menuIndexTask", lockAtLeastFor = "59m", lockAtMostFor = "59m")
    public void generateMenuIndexTask() {
        indexService.generateIdx();
    }

}
