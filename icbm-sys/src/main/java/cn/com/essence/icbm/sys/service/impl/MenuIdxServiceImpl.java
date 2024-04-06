package cn.com.essence.icbm.sys.service.impl;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import cn.com.essence.icbm.sys.common.SearchIdxManager;
import cn.com.essence.icbm.sys.constant.BaseConstant;
import cn.com.essence.icbm.sys.service.IndexService;
import cn.com.essence.wefa.component.bean.Menu;
import cn.com.essence.wefa.component.mapper.MenuMapper;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: huangll
 * @date: 2021-1-27
 *
 * 菜单表索引项更新服务类
 */
@Service
@Slf4j
public class MenuIdxServiceImpl implements IndexService<Menu> {

    @Autowired
    private MenuMapper menuDao;

    @Autowired
    private SearchIdxManager searchIdxManager;

    /**
     *  菜单类
     */
    private static final String MENU_TYPE = "001";

    @Override
    public String getKey(Menu obj) {
        return obj.getMenuId();
    }

    @Override
    public String getName(Menu obj) {
        return obj.getName();
    }

    @Override
    public String getDetail(Menu obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("menuName", obj.getName());
        map.put("path", obj.getPath());
        return JSON.toJSONString(map);
    }

    @Override
    public String isDelete(Menu obj) {
        // 菜单目前没有做逻辑删除，只要存在就是未删除
        return BaseConstant.ISDELETE_N;
    }

    @Override
    public String getType() {
        return MENU_TYPE;
    }

    @Override
    public List<Menu> getDatas() {
        return menuDao.findAllMenus();
    }

    @Override
    public List<SysSearchIdx> getAllSearchIdxItems() {
        return searchIdxManager.getAllSearchItemByType(MENU_TYPE);
    }

    @Override
    public void updateSearchIdxItems(List<SysSearchIdx> updateList) {
        searchIdxManager.updateList(updateList);
    }

    @Override
    public void insertSearchIdxItems(List<SysSearchIdx> newList) {
        searchIdxManager.insertList(newList);
    }


//    @Scheduled(cron = "0 */60 * * * *")
//    @SchedulerLock(name = "menuIndexTask", lockAtLeastFor = "59m", lockAtMostFor = "59m")
//    public void generateMenuIndexTask() {
//        generateIdx();
//    }

}
