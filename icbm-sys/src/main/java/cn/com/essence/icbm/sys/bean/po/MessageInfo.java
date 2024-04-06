package cn.com.essence.icbm.sys.bean.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangjq
 */
@Data
@ApiModel("消息总览")
public class MessageInfo implements Serializable {

    @ApiModelProperty("未读消息数")
    private Integer unread;

    @ApiModelProperty("最新消息接收时间")
    private Long lastMessageTimestamp;

    @ApiModelProperty("最新消息列表")
    private List<SysMessage> lastMessageList;
}
