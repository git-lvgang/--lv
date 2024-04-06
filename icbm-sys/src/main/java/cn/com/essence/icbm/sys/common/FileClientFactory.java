package cn.com.essence.icbm.sys.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import cn.com.essence.wefa.store.s3.StoreManager;


/**
 * @author: huangll
 * @date: 2021-3-4
 */
@Component
public class FileClientFactory {

    @Value("${essence.ambs.basic.store-access-key}")
    private String accessKey;

    @Value("${essence.ambs.basic.store-secret-key}")
    private String secretKey;

    @Value("${essence.ambs.basic.store-name}")
    private String buketName;

    @Value("${essence.ambs.basic.store-server-url}")
    private String serverUrl;

    public StoreManager getClient() {
        return new StoreManager(accessKey, secretKey, serverUrl, buketName);
    }

}
