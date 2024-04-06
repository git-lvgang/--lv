package cn.com.essence.icbm.sys.remote;

import cn.com.essence.icbm.sys.bean.vo.process.OperationReqVo;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessCreateVo;
import cn.com.essence.icbm.sys.remote.Interceptor.ProcessInterceptor;
import cn.com.essence.icbm.sys.remote.adapter.ToStringConverterFactory;
import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * @author: huangll
 * @date: 2021-3-9
 */
//@RetrofitClient(baseUrl = "http://10.5.31.22/api/sys-lbpm/", readTimeoutMs = 100000, converterFactories = ToStringConverterFactory.class)
@RetrofitClient(baseUrl = "${essence.ambs.basic.process-server-base-url}",connectTimeoutMs = 100000, readTimeoutMs = 100000, converterFactories = ToStringConverterFactory.class)
@Intercept(handler = ProcessInterceptor.class)
public interface ProcessStringApi {


    @POST("sysLbpmProcess/create")
    String createProcess(@Body ProcessCreateVo param);


    /**
     * 驱动流程
     */
    @POST("sysLbpmProcess/execute")
    String execute(@Body OperationReqVo operationParameterVO);
}
