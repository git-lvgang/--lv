package cn.com.essence.icbm.sys.bean.vo.process;

import lombok.*;

import java.util.Collections;
import java.util.List;

/**
 * 查询结果
 * 
 * @author huangll
 *
 * @param <V>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult<V> {
	
	private List<V> content;

	private int offset;

	private int pageSize;

	private long totalSize;

	public static <T> QueryResult<T> empty() {
		QueryResult<T> result = new QueryResult<>();
		result.content = Collections.emptyList();
		return result;
	}

}
