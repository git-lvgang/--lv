package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 流程操作参数
 * 
 * @author huangll
 */
@Data
@Accessors(chain = true)
public class OperationReqVo {
	/** 流程标题 */
	private String subject;

	/** 任务ID */
	private String taskId;

	/** 任务类型 */
	private String activityType;

	/** 操作详细参数(json) */
	private String parameter;

	/** 流程实例ID */
	private String processId;

	/** 操作标识（相同操作类型和相同操作身份可能存在多个操作配置） */
	private String operationId;

	/** 操作类型 */
	private String operationType;

	/** 操作身份 */
	private String operationIdentity;

	/** 附加操作参数信息 */
	private List<AdditionOperationParameterVo> additionParameters;

	/**
	 * 表单实例ID
	 */
	private String formInstanceId;
	/**
	 * 表单实例Model Name
	 */
	private String formInstanceModel;

	/** 登录名 */
	private String loginName;
}
