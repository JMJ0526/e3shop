package org.jmj.order.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jmj.bean.TbOrder;
import org.jmj.bean.TbOrderIetm;
import org.jmj.bean.TbOrderShipping;
import org.jmj.exception.CreateOrderException;
import org.jmj.jedis.JedisClientPool;
import org.jmj.mappers.TbOrderIetmMapper;
import org.jmj.mappers.TbOrderMapper;
import org.jmj.mappers.TbOrderShippingMapper;
import org.jmj.order.OrderService;
import org.jmj.order.vo.TbOrderVo;
import org.jmj.util.ManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderIetmMapper orderIetmMapper;
	
	@Autowired
	private TbOrderShippingMapper shippingMapper;
	
	@Autowired
	private JedisClientPool jedisClientPool;
	
	
	@Override
	public void createOrder(TbOrderVo tov,Long userId) throws CreateOrderException {
		
		Long orderid = jedisClientPool.incr("order_key");
		
		String orderId = orderid+""; 
		
		Date date = new Date();
		
		TbOrder order = tov.getOreder();
		order.setOrderId(orderId);
		order.setUpdateTime(date);
		order.setCreateTime(date);
		order.setUserId(userId);
		order.setStatus(1);
		orderMapper.insertSelective(order);
		
		List<TbOrderIetm> list = Arrays.asList(tov.getOrderItems());
		for (TbOrderIetm tbOrderIetm : list) {
			tbOrderIetm.setId(ManagerUtil.newUUIDkey());
			tbOrderIetm.setOrderId(orderId);
		}
		orderIetmMapper.insertList(list);
		
		TbOrderShipping orderShipping = tov.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setUpdated(date);
		orderShipping.setCreated(date);
		shippingMapper.insertSelective(orderShipping);
	}

}
