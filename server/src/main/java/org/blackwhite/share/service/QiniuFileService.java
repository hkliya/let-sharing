package org.blackwhite.share.service;

import java.io.File;

import com.jfinal.kit.PropKit;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuFileService {

	private static final String URL = PropKit.use("ShareConfig.txt").get("qiniu.url", "");
	private static final String ACCESS_KEY = PropKit.use("ShareConfig.txt").get("qiniu.accessKey", "");
	private static final String SECRET_KEY = PropKit.use("ShareConfig.txt").get("qiniu.secretKey", "");
	private static final String BUCKET = PropKit.use("ShareConfig.txt").get("qiniu.bucket", "");
	
	public static String upload(File file,String key){
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String token = auth.uploadToken(BUCKET);
		UploadManager manager = new UploadManager();
		try {
			Response res = manager.put(file, key, token);
			if(res.isOK()){
				DefaultPutRet ret = res.jsonToObject(DefaultPutRet.class);
				return URL + ret.key;
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return "";
	}
}
