package org.jmj.auth.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jmj.auth.UserService;
import org.jmj.bean.TbUser;
import org.jmj.bean.TbUserExample;
import org.jmj.bean.TbUserExample.Criteria;
import org.jmj.bean.TbUserSalt;
import org.jmj.jedis.JedisClientPool;
import org.jmj.mappers.TbUserMapper;
import org.jmj.mappers.TbUserSaltMapper;
import org.jmj.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService{
	
	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private TbUserSaltMapper saltMapper; 
	
	@Autowired
	private JedisClientPool jedisClientPool;
	
	@Override
	public boolean checkParam(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1) {
			criteria.andUsernameEqualTo(param);
			List<TbUser> list = userMapper.selectByExample(example);
			if(list.isEmpty()) {
				return true;
			} 
		}
		
		if(type == 2) {
			criteria.andPhoneEqualTo(param);
			List<TbUser> list = userMapper.selectByExample(example);
			if(list.isEmpty()) {
				return true;
			} 
		}
		return false;
	}

	@Override
	public void doRegister(TbUser user, String salt) throws Exception {
		user.setCreated(new Date());
		user.setUpdated(new Date());
	    userMapper.insertSelective(user);
		
		TbUserSalt userSalt = new TbUserSalt();
		userSalt.setUsername(user.getUsername());
		
		userSalt.setSalt(salt);
		saltMapper.insertSalt(userSalt);
	}

	@Override
	public String getSaltByName(String userName) {
		return saltMapper.selectByName(userName).getSalt();
	}

	@Override
	public TbUser getUserByName(String username) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		TbUser user = list.get(0);
		return user;
	}

	@Override
	public void writeUserCache(String token, String user) {
		jedisClientPool.set(token, user);
		//设置
		jedisClientPool.expire(token, 60 * 20);
	}

	@Override
	public TbUser getUserByRedisCache(String token) {
		String string = jedisClientPool.get(token);
		
		if(StringUtils.isEmpty(string) || string.equals("null")) {
			return null;
		}
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
		return user;
	}

	
	
	
}
