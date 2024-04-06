package cn.com.essence.icbm.sys.service;

import cn.com.essence.icbm.sys.bean.po.SysSearchIdx;
import cn.com.essence.icbm.sys.constant.BaseConstant;
import cn.com.essence.wefa.util.PingYinUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Author: huangll
 * Date: 2021-1-27
 * Description:
 */
public interface IndexService<T> {

    /**
     * 获取当前对象的主键
     * @param obj
     * @return
     */
    String getKey(T obj);

    /**
     * 获取当前对象的名称
     * @param obj
     * @return
     */
    String getName(T obj);

    /**
     * 获取元数据(满足全局搜功能的元数组，从而不需要去源表查询数据)
     * @param obj
     * @return
     */
    String getDetail(T obj);

    /**
     * 获取当前对象的删除标志
     * "0"-未删除
     * "1"-已删除
     * @param obj
     * @return
     */
    String isDelete(T obj);

    /**
     * 获取索引项的类型(菜单-001， 产品-全称-002, 产品-销售代码-003)
     * @return
     */
    String getType();

    /**
     * 获取源表数据
     * @return
     */
    List<T> getDatas();

    /**
     * 获取跟源表相关的索引项数据
     * @return
     */
    List<SysSearchIdx> getAllSearchIdxItems();

    void updateSearchIdxItems(List<SysSearchIdx> updateList);

    void insertSearchIdxItems(List<SysSearchIdx> newList);

    /**
     * 最终一致性
     */
    default int generateIdx() {
        // 查询源表的全部数据
        List<T> datas = getDatas();

        // 查询索引表与源表同类的相关数据
        List<SysSearchIdx> items = getAllSearchIdxItems();

        // 变更列表
        List<SysSearchIdx> updateList = new ArrayList<>();
        // 新增列表
        List<SysSearchIdx> newList = new ArrayList<>();

        // 两个列表 对比差异，生成新增列表和变更列表
        process(datas, items, newList, updateList);

        // 新增索引项
        insertSearchIdxItems(newList);

        // 更新索引项
        updateSearchIdxItems(updateList);

        return newList.size() + updateList.size();

    }



    /**
     two point 实现差异，并做相应的处理（要求有序）
     输入：需要生成的索引的表对部的全部数据项， 已生成索引的跟此表相关的记录项（SysSearchIdx）
     对比两者，存在四种情况(以主键值为匹配规则)
     1.前者后，后者无，后者需要补一条相应记录
     2.前者无，后者有，说明前者已经删除了(理论上应该做逻辑删除，但目前仍存在在没有做逻辑删除的场景)，后者需要删除(逻辑删除)
     3.前者有，后者有，但是需要建索引的名称发生改变，后者对应的记录名称需要做相应变更，
                      如果两者的删除状态不一致，则需要将后者对应的记录的删除状态变更为一致
     4.两者有，后都有，两边一致，不做任何处理

     以需要生成的表为基础，产生一个新增列表，一个变更列表（indexName(索引名称)的修改，isDelete（是否删除）的修改），
     一个变更列表, 作用于索引表
     以下源数据表示需要生成索引的源表数据，目标数据表示已生成的索引数据
     * @param list  要保证有序，排序规则按主键做排序
     * @param items 要保证有序，排序规则按INDEX_KEY排序
     * @param newList 新增列表
     * @param updateList 变更列表(逻辑删除跟修改)
     */
    default void process(List<T> list, List<SysSearchIdx> items, List<SysSearchIdx> newList, List<SysSearchIdx> updateList) {
        int i = 0, j = 0;
        int menuSize = list.size();
        int itemSize = items.size();
        String type = getType();
        Date date = new Date();
        //
        while (i < menuSize) {
            T obj = list.get(i);
            SysSearchIdx item = null;
            int res = -1;
            // 索引表只存小写
            String name = getName(obj).toLowerCase();
            String key = getKey(obj);
            String detail = getDetail(obj);
            String isDeleted = isDelete(obj);
            if (j < itemSize) {
                item = items.get(j);
                res = key.compareTo(item.getIndexKey());
            }
            if (res < 0) {
                // 小于, 说明源表有 ，但是对应索引表还未生成,需要生成
                i ++;
                SysSearchIdx sysSearchIdx = new SysSearchIdx();
                sysSearchIdx.setIndexName(name);
                sysSearchIdx.setIndexPingyin(PingYinUtil.getFirstSpell(name));
                sysSearchIdx.setIndexKey(key);
                sysSearchIdx.setIndexDetail(detail);
                sysSearchIdx.setIndexType(type);
                sysSearchIdx.setCreateTime(date);
                sysSearchIdx.setUpdateTime(date);
                sysSearchIdx.setIsDeleted(BaseConstant.ISDELETE_N);
                newList.add(sysSearchIdx);

            } else if (res == 0) {
                // 对应的记录，需要判断下有没有发生修改或删除
                SysSearchIdx updateItem = null;
                if (!Objects.equals(name, item.getIndexName())) {
                    // 索引名称发生修改
                    updateItem = new SysSearchIdx();
                    updateItem.setId(item.getId());
                    updateItem.setIndexName(name);
                    updateItem.setIndexPingyin(PingYinUtil.getFirstSpell(name));
                    updateItem.setUpdateTime(date);
                }
                if (!Objects.equals(isDeleted, item.getIsDeleted())) {
                    // 两边删除状态不一致，以源表的状态更新索引表
                    if (updateItem == null) {
                        updateItem = new SysSearchIdx();
                        updateItem.setId(item.getId());
                        updateItem.setUpdateTime(date);
                    }
                    updateItem.setIsDeleted(isDeleted);
                }
                if (!Objects.equals(detail, item.getIndexDetail())) {
                    // 元数据有更新，需要处理
                    if (updateItem == null) {
                        updateItem = new SysSearchIdx();
                        updateItem.setId(item.getId());
                        updateItem.setUpdateTime(date);
                    }
                    updateItem.setIndexDetail(detail);
                }
                if (updateItem != null) {
                    updateList.add(updateItem);
                }
                i++;
                j++;
            } else {
                // 源数据无，目标数据有，需要将目标数据置为已删除（逻辑删除）
                j ++;
                SysSearchIdx deleteItem = new SysSearchIdx();
                deleteItem.setId(item.getId());
                deleteItem.setIsDeleted(BaseConstant.ISDELETE_Y);
                deleteItem.setUpdateTime(date);
                updateList.add(deleteItem);
            }

        }

        while (j < itemSize) {
            // 源数据无，目标数据有，需要将目标数据置为已删除（逻辑删除）
            SysSearchIdx deleteItem = new SysSearchIdx();
            deleteItem.setId(items.get(j).getId());
            deleteItem.setIsDeleted(BaseConstant.ISDELETE_Y);
            deleteItem.setUpdateTime(date);
            updateList.add(deleteItem);
            j ++;
        }
    }
}
