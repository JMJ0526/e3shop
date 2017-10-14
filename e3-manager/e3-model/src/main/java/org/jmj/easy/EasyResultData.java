package org.jmj.easy;

import java.io.Serializable;
import java.util.List;

/**
 * 描述: 封装EasyUI参数的对象
 * @author jmj
 */
public class EasyResultData<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8735786853743400258L;

	public EasyResultData(long total,List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	private long total;
	
	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
