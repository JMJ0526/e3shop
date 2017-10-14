package org.jmj.auth;

import org.jmj.bean.TbUser;

public interface UserService {
	
	
	/**
	 * 检查用户名和手机名
	 * @param param
	 * @param type
	 * @return
	 */
	public boolean checkParam(String param,int type);
	
	
	/**
	 * 注册用户
	 * @param user
	 */
	public void doRegister(TbUser user,String salt) throws Exception;
	
	/**
	 * 通过ID获取盐
	 * @param id
	 * @return
	 */
	public String getSaltByName(String userName);
	
	
	/**
	 * 通过用户名获取用户信息
	 * @param username
	 * @return
	 */
	public TbUser getUserByName(String username);
	
	/**
	 * 将登录用户写入redis
	 * @param token
	 * @param user
	 */
	public void writeUserCache(String token,String user);
	
	
	/**
	 * 从reids中获取token
	 * @param token
	 * @return
	 */
	public TbUser getUserByRedisCache(String token);
	
}
