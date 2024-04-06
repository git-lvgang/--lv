package cn.com.essence.icbm.sys.remote;

import cn.com.essence.icbm.sys.remote.Interceptor.ProcessInterceptor;
import cn.com.essence.icbm.sys.bean.vo.process.*;
import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;


/**
 * @author: huangll
 * @date: 2021-3-9
 */
@RetrofitClient(baseUrl = "${essence.ambs.basic.process-server-base-url}", connectTimeoutMs = 100000, readTimeoutMs = 100000)
@Intercept(handler = ProcessInterceptor.class)
public interface ProcessApi {

    @POST("sysLbpmTemplate/getTemplates")
    List<ProcessTemplateVo> getTemplates(@Body TemplateParam param);


//    @POST("sysLbpmProcess/create")
//    Call<String> createProcess(@Body ProcessCreateVo param);
//
//    /**
//     * 驱动流程
//     */
//    @POST("sysLbpmProcess/execute")
//    Map<String, Object> execute(@Body OperationReqVo operationParameterVO);

    /**
     * 查看流程
     */
    @POST("sysLbpmProcess/getProcessInfo")
    ProcessInfo getProcess(@Body ProcessFormVo processFormVo);


    /**
     * 列表接口
     */
    @POST("sysLbpmProcess/list")
    QueryResult<ProcessVo> getProcesslist(@Body ProcessListParamVo parameterVO);


}
