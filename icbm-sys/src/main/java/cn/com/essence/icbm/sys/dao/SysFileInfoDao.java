package cn.com.essence.icbm.sys.dao;

import cn.com.essence.icbm.sys.bean.po.SysFileInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: huangll
 * @date: 2021-3-15
 */
@Mapper
public interface SysFileInfoDao {
    int deleteByPrimaryKey(String fileId);

    int insert(SysFileInfo record);

    int insertSelective(SysFileInfo record);

    SysFileInfo selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(SysFileInfo record);

    int updateByPrimaryKey(SysFileInfo record);

    List<SysFileInfo> selectFiles(@Param("fileIds") List<String> fileIds);

    List<JSONObject> selectFileList(@Param("reportIds") List<String> reportIds);
}