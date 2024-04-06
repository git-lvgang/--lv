package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysNotice;
import cn.com.essence.icbm.sys.bean.vo.SysUserNoticeVo;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysNoticeDao {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    SysNotice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

    List<SysUserNoticeVo> findPage(Pager<SysUserNoticeVo> pager);

    int findPageCount(Pager<SysUserNoticeVo> pager);
}