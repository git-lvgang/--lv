package cn.com.essence.icbm.sys.common;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import cn.com.essence.icbm.sys.dao.SysSearchIdxDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-28
 * Description:
 */
@Component
@Slf4j
public class SearchIdxManager {

    @Autowired
    private SysSearchIdxDao sysSearchIdxDao;

    /**
     * 获取索引类型为type的所有记录, 按INDEX_KEY排序
     * @param type
     * @return
     */
    public List<SysSearchIdx> getAllSearchItemByType(String type) {
        int pos = 0;
        int num = 1000;

        List<SysSearchIdx> resList = new ArrayList<>();
        while(true) {
            List<SysSearchIdx> list = sysSearchIdxDao.findByType(type, pos, num);
            resList.addAll(list);
            if (list.size() < num) {
                break;
            }
            pos += num;
        }
        return resList;
    }

    public void updateList(List<SysSearchIdx> updateList)  {
        // 变更记录
        updateList.forEach(item -> {
            try {
                sysSearchIdxDao.updateByPrimaryKeySelective(item);
                log.debug("update sysSerachIdx<{}>", item);
            } catch (Exception e) {
                log.error("update sysSerachIdx<{}> error, msg:<{}>", e.getMessage());

            }
        });
    }

    public void insertList(List<SysSearchIdx> list) {
        list.forEach(item -> {
            try {
                sysSearchIdxDao.insert(item);
                log.debug("insert sysSerachIdx<{}>", item);
            } catch (Exception e) {
                // 唯一约束，插入的就不做处理了
                log.error("insert sysSerachIdx<{}> error, msg:<{}>", e.getMessage());
            }
        });
    }
}
