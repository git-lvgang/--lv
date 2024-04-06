package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程模版
 * 
 * @author huangll
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessTemplateVo {

	private String fdId;

	private String fdSubject;

}
