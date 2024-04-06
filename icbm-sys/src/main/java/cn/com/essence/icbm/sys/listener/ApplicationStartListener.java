package cn.com.essence.icbm.sys.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SuppressWarnings("rawtypes")
public class ApplicationStartListener implements ApplicationListener, Ordered {

    public static final String API_BOOT_LISTENER_PREFIX = "api.boot.listener";


    /**
     * Springboot 工程启动/关闭监听器
     * @param event
     */
    @Override
    public void onApplicationEvent(@NonNull ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            // 初始化环境变量
            log.info("==========初始化环境变量==============");
            ConfigurableEnvironment environment = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();
            String datasourceUrl = environment.getProperty("spring.datasource.url");
            log.info("数据库连接url: {}", datasourceUrl);
        } else if (event instanceof ApplicationPreparedEvent) {
            // 初始化完成
            log.info("==========初始化完成==============");
        } else if (event instanceof ContextRefreshedEvent) {
            // 应用刷新
            log.info("==========应用刷新==============");
        } else if (event instanceof ApplicationReadyEvent) {
            // 应用已启动完成
            String server = ((ApplicationReadyEvent) event).getSpringApplication().getAllSources().iterator().next()
                    .toString();
            log.info("系统[{}]启动完成!!!", server.substring(server.lastIndexOf(".") + 1));
        } else if (event instanceof ContextStartedEvent) {
            // 应用启动，需要在代码动态添加监听器才可捕获
            log.info("==========应用启动==============");
        } else if (event instanceof ContextStoppedEvent) {
            // 应用停止
            log.info("==========应用停止==============");
        } else if (event instanceof ContextClosedEvent) {
            // 应用关闭
            log.info("==========应用关闭==============");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 5;
    }
}