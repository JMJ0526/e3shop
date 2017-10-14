package org.jmj.fastdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDfsClient {
	
	private TrackerClient client = null;
	private TrackerServer server = null;
	private StorageServer storageServer = null;
	private StorageClient storageClient = null;
	
	
	public void init() {
		try {
			ClientGlobal.init("fdfs_client.conf");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	private  StorageClient getStorageServer() {
		client = new TrackerClient();
		try {
			server = client.getConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		storageClient = new StorageClient(server, storageServer);
		return storageClient;
	}
	
	
	//上传
	public String uploadFile(byte[] fbuff,String extname,String orginname) {
		StringBuilder sb = new StringBuilder("http://192.168.1.102");
		NameValuePair[] nvp = new NameValuePair[1];
		nvp[0] = new NameValuePair("filename",orginname);
		try {
			String[] file = getStorageServer().upload_file(fbuff, extname, nvp);
			for (String string : file) {
				sb.append("/"+string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
