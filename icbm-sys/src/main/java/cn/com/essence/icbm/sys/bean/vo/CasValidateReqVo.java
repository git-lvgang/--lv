package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CasValidateReqVo implements Serializable {

    private String ticket;

    private String service;
    /**
     * 验证码的唯一标识
     */
    private String codeId;

    /**
     * 存储对称加密秘钥的密文
     */
    private String key;

    private String clientIp;

    private String waitId;

    private String data;

}
