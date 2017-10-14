package org.jmj.order;

import org.jmj.exception.CreateOrderException;
import org.jmj.order.vo.TbOrderVo;

public interface OrderService {
	
	
	/**
	 * 创建订单
	 * @param tov
	 * @param userId
	 * @throws CreateOrderException
	 */
	public void createOrder(TbOrderVo tov,Long userId) throws CreateOrderException;
}
