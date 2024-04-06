package cn.com.essence.icbm.sys.controller;

import cn.com.essence.icbm.sys.bean.vo.SearchInfo;
import cn.com.essence.icbm.sys.service.SearchService;
import cn.com.essence.wefa.core.bean.CommonRspVo;
import cn.com.essence.wefa.core.bean.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */

@RestController
@Slf4j
@Api("全局搜索")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    @ApiOperation("搜索")
    public CommonRspVo searchMatchItem(@RequestParam("keyword") String keyWord) {
        log.info("全局搜索关键字<{}>", keyWord.replaceAll("[\r\n]",""));

        // 检查参数
        if (StringUtils.isEmpty(keyWord)) {
            return new CommonRspVo(ResultCode.C_PARAMS_ERROR);
        }

        SearchInfo result = searchService.getSerachResult(keyWord.toLowerCase());
        return new CommonRspVo(ResultCode.C_SUCCESS, result);
    }
}
