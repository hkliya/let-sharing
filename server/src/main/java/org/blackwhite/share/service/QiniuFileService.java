package org.blackwhite.share.service;

import java.io.File;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuFileService {

	private static final String URL = "http://7ryl6g.com1.z0.glb.clouddn.com/";
	
	public static String upload(File file,String key){
		Auth auth = Auth.create("i8XeRxx4ifM_6sXAZN4q5kwe98ZAP3ic2lDn5OZZ", 
				"EhM2W7jU8DxZs25V1zxALN9FOikrUx0HFnRBud8C");
		String token = auth.uploadToken("qiju");
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
