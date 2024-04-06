package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.vo.SearchInfo;


/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */
public interface SearchService {
    /**
     * 根据关键字搜索
     * @param keyWord
     * @return
     */
    SearchInfo getSerachResult(String keyWord);
}
