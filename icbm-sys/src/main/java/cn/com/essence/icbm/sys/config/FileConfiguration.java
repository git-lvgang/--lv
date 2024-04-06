package cn.com.essence.icbm.sys.config;

import cn.com.essence.wefa.util.PreviewClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: huangll
 * @date: 2021-3-3
 */
@Configuration
public class FileConfiguration {

    @Value("${essence.ambs.basic.preview-server-url-prefix}")
    private String previewServer;

    @Value("${essence.ambs.basic.preview-server-app-id}")
    private String appId;

    @Bean
    public PreviewClient previewClient() {
        return new PreviewClient(previewServer, appId);
    }

    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //multipartResolver.setMaxUploadSizePerFile(10*1024*1024);
        return multipartResolver;
    }
}
