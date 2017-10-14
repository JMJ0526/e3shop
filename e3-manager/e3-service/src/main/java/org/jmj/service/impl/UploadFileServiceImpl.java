package org.jmj.service.impl;

import org.jmj.fastdfs.FastDfsClient;
import org.jmj.service.fastdfs.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadFileServiceImpl implements UploadFileService {
	
	@Autowired
	private FastDfsClient fastDfsClient;
	
	@Override
	public String uploadFile(byte[] fbuff, String extname, String orginname) {
		return fastDfsClient.uploadFile(fbuff, extname, orginname);
	}

}
