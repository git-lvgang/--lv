package cn.com.essence.icbm.sys.config;

import cn.com.essence.icbm.sys.common.S3FileWriter;
import cn.com.essence.wefa.util.MailClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huangll
 * @date: 2021-3-20
 */
@Configuration
public class MailConfiguration {
    /**
     * 邮件服务器
     */
    @Value("${essence.ambs.basic.mail.server}")
    private String hostServer;

    /**
     * 用户名
     */
    @Value("${essence.ambs.basic.mail.username}")
    private String userName;

    /**
     * 密码
     */
    @Value("${essence.ambs.basic.mail.password}")
    private String password;

    @Bean
    public MailClient mailClient(@Qualifier("s3FileWriter") S3FileWriter fileWriter) {
        MailClient client = new MailClient();
        client.setHostServer(hostServer);
        client.setUsername(userName);
        client.setPassword(password);
        // S3存储类
        client.setFileWriter(fileWriter);
        return client;
    }
}
