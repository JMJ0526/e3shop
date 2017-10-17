package org.jmj.mappers;

import org.jmj.bean.TbUserSalt;

public interface TbUserSaltMapper {
	
	public TbUserSalt selectByName(String username);
	
	public void insertSalt(TbUserSalt tbSalt);
}
