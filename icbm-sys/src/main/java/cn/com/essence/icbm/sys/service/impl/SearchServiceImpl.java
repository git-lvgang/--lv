package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import cn.com.essence.icbm.sys.bean.vo.FundInfo;
import cn.com.essence.icbm.sys.bean.vo.SearchBaseInfo;
import cn.com.essence.icbm.sys.bean.vo.SearchInfo;
import cn.com.essence.icbm.sys.bean.vo.SearchMenuInfo;
import cn.com.essence.icbm.sys.dao.PlcFundBaseDao;
import cn.com.essence.icbm.sys.dao.SysSearchIdxDao;
import cn.com.essence.icbm.sys.login.LoginService;
import cn.com.essence.icbm.sys.service.SearchService;
import cn.com.essence.wefa.component.bean.Menu;
import cn.com.essence.wefa.component.mapper.MenuMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-26
 * Description:
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SysSearchIdxDao sysSearchIdxDao;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LoginService longinService;

    @Autowired
    private PlcFundBaseDao fundBaseDao;

    private static final int LIMIT_NUM = 20;

    /**
     * 菜单类
     */
    private static final String MENU_TYPE = "001";

    /**
     * 产品类-全名
     */
    private static final String FUND_NAME_TYPE = "002";

    /**
     * 产品类-销售代码
     */
    private static final String FUND_SALE_CODE = "003";

    /**
     * 产品列表路径
     */
    private static final String FUND_PATH = "/plc/core/360";
    private List<String> getMenu(){
        List<String> list=new ArrayList<>();
        List<Menu> menus=longinService.getUserMenus();
        for (Menu m:menus){
            list.add(m.getName());
            List<Menu> m1=m.getChildren();
            for(Menu mm:m1){
               list.add(mm.getName());
            }
        }
        return list;
    }
    /**
     * 查询结果
     * @param keyWord
     * @return
     */
    @Override
    public SearchInfo getSerachResult(String keyWord) {
        SearchInfo searchInfo = new SearchInfo();
        List<SearchMenuInfo> menuInfos = new ArrayList<>();
        // 菜单类处理
        SearchBaseInfo<FundInfo> searchFundInfo = new SearchBaseInfo<>();
        searchInfo.setMenus(menuInfos);
        // 查询
        List<SysSearchIdx> list = sysSearchIdxDao.selectList(keyWord, MENU_TYPE, LIMIT_NUM);
        Set<SysSearchIdx> l = new HashSet<>();
        List<String> li=getMenu();
        for(SysSearchIdx sysSearchIdx:list){
            for(String str:li){
                if(sysSearchIdx.getIndexName().equals(str)){
                    l.add(sysSearchIdx);
                    break;
                }
                String indexKey=sysSearchIdx.getIndexKey().substring(0,5);
                String name=sysSearchIdxDao.queryIndexName(indexKey);
                if(name.equals(str)){
                    l.add(sysSearchIdx);
                }
            }
        }
        l.forEach(x -> {
            try {
                if (!StringUtils.isEmpty(x.getIndexDetail())) {
                    SearchMenuInfo menuInfo = JSON.parseObject(x.getIndexDetail(), SearchMenuInfo.class);
                    menuInfos.add(menuInfo);
                } else {
                    log.error("sys_search_idx存在indexDetail为空的记录，id为<{}>", x.getId());
                }
            } catch (Exception e) {
                log.error("indexDetail<{}>转换异常", x.getIndexDetail());
            }
        });


        searchInfo.setFund(searchFundInfo);
        // TODO 先写死， 这个后续做成配置吧
        searchFundInfo.setPath(FUND_PATH);
        List<FundInfo> fundInfos = new ArrayList<>();
        searchFundInfo.setList(fundInfos);
        // 查询产品（产品全称跟销售代码,这里面可能会有重合的,需要做去重）
        list = sysSearchIdxDao.selectList(keyWord, FUND_NAME_TYPE, LIMIT_NUM);
        list.addAll(sysSearchIdxDao.selectList(keyWord, FUND_SALE_CODE, LIMIT_NUM));

        // 用于去重，产品支持产品全称跟销售代码的搜索
        Set<Integer> set = new HashSet<>();
        for (SysSearchIdx x : list) {
            try {
                if (!StringUtils.isEmpty(x.getIndexDetail())) {
                    if (!set.contains(x.getId())) {
                        // 未在去重集合中
                        FundInfo fundInfo = JSON.parseObject(x.getIndexDetail(), FundInfo.class);
                        fundInfos.add(fundInfo);
                        set.add(fundInfo.getFundId());

                        if (set.size() == LIMIT_NUM) {
                            // 只查询前LIMIT_NUM个,够了就退出循环
                            // 目前也没有所谓的优先级
                            break;
                        }
                    }
                } else {
                    log.error("sys_search_idx存在indexDetail为空的记录，id为<{}>", x.getId());
                }
            } catch (Exception e) {
                log.error("indexDetail<{}>转换异常,id<{}>", x.getIndexDetail(), x.getId());
            }
        }
        return searchInfo;
    }

}
