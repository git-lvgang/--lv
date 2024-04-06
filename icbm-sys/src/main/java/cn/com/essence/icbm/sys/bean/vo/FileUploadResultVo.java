package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: huangll
 * @date: 2021-3-6
 */
@Data
public class FileUploadResultVo implements Serializable {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     *  上传时间
     */
    private Date uploadTime;


    /**
     * 文件大小
     */

    private long fileSize;
}
