package org.jmj.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jmj.auth.UserService;
import org.jmj.bean.TbItem;
import org.jmj.bean.TbUser;
import org.jmj.cart.CartService;
import org.jmj.util.JsonUtils;
import org.jmj.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SyncCartInterceptor implements HandlerInterceptor {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		TbUser token = null;
		String string = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isNotEmpty(string)) {
			token = userService.getUserByRedisCache(string);
		}

		if (token == null) {
			return true;
		}
		
		SyncCart SyncCart = new SyncCart(token.getId(), cartService, request, response);
		SyncCart.start();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	private class SyncCart extends Thread {

		CartService cartService;
		HttpServletRequest request;
		Long userId;

		public SyncCart(Long userId, CartService cartService, HttpServletRequest request,
				HttpServletResponse response) {
			this.userId = userId;
			this.cartService = cartService;
			this.request = request;
		}

		@Override
		public void run() {
			HashMap<Long, Integer> map = new HashMap<>();
			// 同步购物车
			String string = cartService.getCartByRedis("userCart_" + userId, "cart");
			List<TbItem> items = null;
			if (StringUtils.isNotEmpty(string)) {
				if (!string.equals("null")) {
					items = JsonUtils.jsonToList(string, TbItem.class);
					for (int i = 0; i < items.size(); i++) {
						map.put(items.get(i).getId(), i);
					}
				}
			}
			String value = CookieUtils.getCookieValue(request, "cart", "UTF-8");
			// 本地购物车不为空
			if (StringUtils.isNotEmpty(value) && !value.equals("null")) {
				List<TbItem> list = JsonUtils.jsonToList(value, TbItem.class);
				for (TbItem tbItem : list) {
					Long id = tbItem.getId();
					if (map.containsKey(id)) {
						TbItem item = items.get(map.get(id));
						item.setNum(tbItem.getNum());
					} else {
						items.add(tbItem);
					}
				}
			}

			String result = JsonUtils.objectToJson(items);
			cartService.setCartToRedis("userCart_5", "cart", result);
			//CookieUtils.setCookie(request, response, "cart", result, 3600, "UTF-8");
		}
	}

}
