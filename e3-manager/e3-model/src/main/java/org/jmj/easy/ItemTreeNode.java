package org.jmj.easy;

import java.io.Serializable;

/**
 * 描述:后台添加item时所需要选择类目的树节点对象
 * @author jmj
 */
public class ItemTreeNode  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1998929743793702407L;

	private Long id;
	
	/**
	 * itemCatName
	 */
	private String text;
	
	private String state;

	
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public ItemTreeNode(Long id, String text,String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	
	
	
}
