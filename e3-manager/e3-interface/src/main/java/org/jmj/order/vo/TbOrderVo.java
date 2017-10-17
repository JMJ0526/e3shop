package org.jmj.order.vo;

import java.io.Serializable;

import org.jmj.bean.TbOrder;
import org.jmj.bean.TbOrderIetm;
import org.jmj.bean.TbOrderShipping;


public class TbOrderVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private TbOrder oreder;
    
	private TbOrderIetm[] orderItems;
	
	private TbOrderShipping orderShipping;

	public TbOrder getOreder() {
		return oreder;
	}

	public void setOreder(TbOrder oreder) {
		this.oreder = oreder;
	}

	public TbOrderIetm[] getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(TbOrderIetm[] orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	
	
}
