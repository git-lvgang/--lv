package cn.com.essence.icbm.sys.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: huangll
 * @date: 2021-3-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 上传时间
     */
    private Date uploadTime;
}
