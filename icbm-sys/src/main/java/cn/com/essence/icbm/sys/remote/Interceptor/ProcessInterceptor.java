package cn.com.essence.icbm.sys.remote.Interceptor;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: huangll
 * @date: 2021-3-9
 */
@Component
public class ProcessInterceptor extends BasePathMatchInterceptor {
    @Value("${essence.ambs.basic.process-authorization}")
    private String processAuthorization;
    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder().addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Basic " +  processAuthorization).build();
        return chain.proceed(newReq);
    }
}
