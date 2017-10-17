package org.jmj.util;

import java.util.UUID;

public final class ManagerUtil {
	
	
	/**
	 * 返回一个随机long ID
	 * @return
	 */
	public static Long newPrimaryKey() {
		return  System.currentTimeMillis() / 10000;
	}
	
	
	/**
	 * 返回盐值
	 * @return
	 */
	public static String newSalt() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
	}
	
	
	/**
	 * 16位uuid主键
	 * @return
	 */
	public static String newUUIDkey() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
	}
}
