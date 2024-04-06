package cn.com.essence.icbm.sys.bean.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileInfoExt extends SysFileInfo{

    private byte[] buffer;
}
