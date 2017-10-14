package org.jmj.cart.impl;

import org.jmj.cart.CartService;
import org.jmj.jedis.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClientPool jedisClientPool;
	
	@Override
	public String getCartByRedis(String hkey, String field) {
		return jedisClientPool.hget(hkey, field);
	}

	@Override
	public void setCartToRedis(String hkey, String field, String value) {
		jedisClientPool.hset(hkey, field, value);
	}

}
