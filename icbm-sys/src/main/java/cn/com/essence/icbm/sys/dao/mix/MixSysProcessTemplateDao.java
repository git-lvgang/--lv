package cn.com.essence.icbm.sys.dao.mix;

import cn.com.essence.icbm.sys.bean.po.mix.MixSysProcessTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MixSysProcessTemplateDao {

    List<MixSysProcessTemplate> selectAll();

}
