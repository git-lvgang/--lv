package cn.com.essence.icbm.sys.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SsoValidateVo implements Serializable {

  private String taskId;
  private String type;
  private String procInstId;
  private boolean notice;
  private String waitId;
  private String action;
  private String data;

  private String logincert;
  private String ssocert;

  /**
   * 存储对称加密秘钥的密文
   */
  private String key;
}
