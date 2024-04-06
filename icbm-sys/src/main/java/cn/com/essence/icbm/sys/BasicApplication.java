package cn.com.essence.icbm.sys;

import cn.com.essence.wefa.spring.boot.autoconfigure.ShiroAutoConfigure;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"cn.com.essence.icbm.sys"}, exclude = {ShiroAutoConfigure.class})
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@EnableTransactionManagement
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

}

