package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysProcessTemplate;
import cn.com.essence.icbm.sys.bean.vo.process.ProcessTemplateReqVo;
import cn.com.essence.wefa.core.mybatis.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysProcessTemplateDao {
    int deleteByPrimaryKey(String templateId);

    int insert(SysProcessTemplate record);

    int insertSelective(SysProcessTemplate record);

    SysProcessTemplate selectByPrimaryKey(String templateId);

    int updateByPrimaryKeySelective(SysProcessTemplate record);

    int updateByPrimaryKey(SysProcessTemplate record);

    List<SysProcessTemplate> findPage(Pager<SysProcessTemplate> pager);

    int findPageCount(Pager<SysProcessTemplate> pager);

    List<SysProcessTemplate> selectAll();

    String queryTemplateName(String id);

    List<ProcessTemplateReqVo> selectProcesTemplates(Pager<ProcessTemplateReqVo> pager);

    void updateSecuritiesStatus(@Param("categoryId") String categoryId,@Param("secuIntl") String secuIntl,@Param("status") String status);
}