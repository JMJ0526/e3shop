/**
 * 
 */
package org.jmj.realms;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.jmj.auth.UserService;
import org.jmj.bean.TbUser;

public class LoginRealm extends AuthenticatingRealm{
	
	@Resource
	private UserService userService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		String salt = userService.getSaltByName(username); //从数据库中获取盐值
		ByteSource bs = new SimpleByteSource(salt);
		TbUser user = userService.getUserByName(username);
		return new SimpleAuthenticationInfo(username,user.getPassword(),bs,"loginRealm");
	}
	
}
