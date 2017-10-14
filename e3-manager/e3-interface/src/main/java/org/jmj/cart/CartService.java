package org.jmj.cart;

public interface CartService {
	
	/**
	 * 获取redis中的购物车列表
	 * @param hkey
	 * @param field
	 * @return
	 */
	public String getCartByRedis(String hkey,String field);
	
	
	/**
	 * 将购物车列表写入redis
	 * @param hkey
	 * @param field
	 * @param value
	 */
	public void setCartToRedis(String hkey,String field,String value);
}
