package org.jmj.easy;

import java.io.Serializable;

/**
 * 描述:
 * @author jmj
 */
public class SearchItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object id;
	
	private String[] images;
	
	private Object title;
	
	private Object sellPoint;
	
	private Object price;
	
	private int num;
	
	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Object getTitle() {
		return title;
	}

	public void setTitle(Object title) {
		this.title = title;
	}

	public Object getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(Object sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Object getPrice() {
		return price;
	}

	public void setPrice(Object price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
}
