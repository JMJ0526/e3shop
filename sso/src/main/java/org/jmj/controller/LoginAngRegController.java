package org.jmj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.jmj.auth.UserService;
import org.jmj.bean.TbUser;
import org.jmj.easy.ResponseResult;
import org.jmj.util.JsonUtils;
import org.jmj.util.ManagerUtil;
import org.jmj.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginAngRegController {

	@Autowired
	private UserService userService;


	@RequestMapping("/page/login")
	public String pageLogin() {
		return "login";
	}

	@RequestMapping("/page/register")
	public String pageRegis() {
		return "register";
	}

	/**
	 * 检查用户名或者手机是否可用
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("user/check/{param}/{type}")
	public ResponseResult checkParams(@PathVariable("param") String param, @PathVariable("type") int type) {
		boolean b = userService.checkParam(param, type);
		return new ResponseResult(200, b ? "可用" : "不可用", b);
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/register")
	public ResponseResult doRegister(TbUser user) {
		String salt = ManagerUtil.newSalt();
		Md5Hash md5 = new Md5Hash(user.getPassword(), salt);
		user.setPassword(md5.toString());
		try {
			userService.doRegister(user, salt);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(500, "", null);
		}
		return new ResponseResult(200, "", null);
	}

	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/login")
	public ResponseResult doLogin(TbUser user, HttpServletRequest request, HttpServletResponse response) {
		// 登录
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		try {
			subject.login(token);
			// token id
			String t = ManagerUtil.newSalt();
			CookieUtils.setCookie(request, response, "token", t,-1);

			TbUser tbUser = userService.getUserByName(user.getUsername());
				
			userService.writeUserCache(t, JsonUtils.objectToJson(tbUser));

		} catch (AuthenticationException e) {
			return new ResponseResult(500, "", "");
		}
		return ResponseResult.ok();
	}

	/**
	 * jsonp 跨域
	 * @param token
	 * @param callback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/token/{token}")
	public Object getUserByToken(@PathVariable("token") String token, String callback) {
		TbUser tbUser = userService.getUserByRedisCache(token);
		ResponseResult result = new ResponseResult(200, "", tbUser);

		if (StringUtils.isNotEmpty(callback)) {
			MappingJacksonValue val = new MappingJacksonValue(result);
			val.setJsonpFunction(callback);
			return val;
		}

		return result;
	}
	
}
